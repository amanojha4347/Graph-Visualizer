
package projectnodeslinkdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class DFS {
    FrmAnimation ani;
    ProjectNodesLinkDraw dr;
    HashMap<Integer,ArrayList<Integer>> graph = new HashMap<>();
    HashMap<Integer,String> path = new HashMap<>();
    boolean visited[];
    int ballin=-1,ballfin=-1;
    int source=1;
    public static int V;
    ArrayList<Integer> trav = new ArrayList<>();
    
    public DFS(FrmAnimation ani) {
        this.ani=ani;
        dr=ani.dr;
        V=ani.dr.balls.size()+1;
        visited=new boolean[V];  
        setUp();      
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, 1080, 720);
        g.setColor(Color.BLACK);
        Graphics2D g2= (Graphics2D)g;
        for(int i=0;i<1080;i+=100) {
            g.drawLine(i, 0, i, 720);
        }
        for(int i=0;i<720;i+=100) {
            g.drawLine(0, i, 1080, i);
        }
        for(ProjectNodesLinkDraw.Ball x:ani.dr.balls) {
            if(x.id==source) {
                g.setColor(Color.pink);
            }
            else if(visited[x.id]) {
                g.setColor(Color.YELLOW);
            }
            else 
                g.setColor(Color.RED);
            if(x.id==ballin || x.id==ballfin) {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.fillOval(x.x-15, x.y-15, 30, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospaced", Font.BOLD, 14));
            g.drawString(""+x.id, x.x-4, x.y+5);
        }
        g.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(3));
        for(ProjectNodesLinkDraw.Edge edge:ani.dr.edges)  {
            if((edge.ballin==ballin && edge.ballfin==ballfin) || (edge.ballfin==ballin && edge.ballin==ballfin)) {
                g.setColor(Color.blue);
            }
            else 
                g.setColor(Color.GREEN);
            g2.drawLine(edge.x1, edge.y1,edge.x2 , edge.y2);
        }
    }
    
    public void setUp() {
        for(ProjectNodesLinkDraw.Ball x:dr.balls) {
            graph.put(x.id,new ArrayList<Integer>());
        }
        for(ProjectNodesLinkDraw.Edge x: dr.edges) {
            graph.get(x.ballin).add(x.ballfin);
            graph.get(x.ballfin).add(x.ballin);
        }
    }
    
    public void DFS(int src) throws InterruptedException {        
        Stack<Integer> q = new Stack<>();
        source=src;
        q.push(src);
        ballin=src;

        while(!q.isEmpty()) {
            int k=q.pop();
            trav.add(k);
            visited[k]=true;
            ani.repaint(4000);
            ballfin=k;

            for(Integer n:graph.get(k)) {
                if(!visited[n]) {
                    visited[n]=true;
                    q.push(n);
                    ballfin=n;
                    ani.repaint(4000);
                }
                else {
                    ballfin=n;
                    ani.repaint(200);
                }
            }
            ballin=-1;
            ballfin=-1;
            ani.repaint();
            
        }
        for(int x:trav)
                System.out.print(x+" ");
    }
    
    public void DFS2(int src,String Path) throws InterruptedException {
        
        if(visited[src])
            return;
        visited[src]=true;
        path.put(src,Path);
        ballin=src;
        trav.add(src);
        ani.repaint(2000);
        
        for(int x:graph.get(src)) {
            ballfin=x;
            if(!visited[x])
                ani.repaint(2000);
            DFS2(x,Path+" "+x);
        }
        ballin=-1;
        ballfin=-1;
        ani.repaint();
    }
}
