
package projectnodeslinkdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


public class pnlMatrix extends JPanel implements Runnable{

    FrmAnimation dik;
    DFS dfs;
    BFS bfs;
    
    public pnlMatrix(FrmAnimation dik) {
        this.dik=dik;
        dfs=dik.dfs;
        bfs=dik.bfs;
        setPreferredSize(new Dimension(500, 720));
    }
    
    public void paintBFS(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 500, 720);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.BOLD, 35));
        g.drawString("Path",110,30);
        int j=70,x=0;
        
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        for(Map.Entry<Integer,String> e:bfs.path.entrySet()) {
            String s= e.getValue();
            String sr[] = s.split(" ");
            g.drawString(""+e.getKey()+" : ", x+=15, j+=30);
            
            for(int i=0;i<sr.length;i++) {
                if(e.getKey()== Integer.parseInt(sr[i])) {
                    g.drawString(""+sr[i]+"", x+=30, j);
                }
                else {
                    g.drawString(""+sr[i]+"->", x+=30, j);
                }
            }
            x=0;
        }
    }
    
    public void paintDFS(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 500, 720);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.BOLD, 35));
        g.drawString("Path",110,30);
        int j=70,x=0;
        
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        for(Map.Entry<Integer,String> e:dfs.path.entrySet()) {
            String s= e.getValue();
            String sr[] = s.split(" ");
            g.drawString(""+e.getKey()+" : ", x+=15, j+=30);
            
            for(int i=0;i<sr.length;i++) {
                if(e.getKey()== Integer.parseInt(sr[i])) {
                    g.drawString(""+sr[i]+"", x+=30, j);
                }
                else {
                    g.drawString(""+sr[i]+"->", x+=30, j);
                }
            }
            x=0;
        }
    }
    
    public void paintDikstra(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 500, 720);
        g.setColor(Color.BLACK);
        
        for(int i=0;i<dik.dik.dist.length+1;i++) {
            g.drawLine(20, 30*i, 120, 30*i);
        }
        
        g.drawLine(20, 20, 20, dik.dik.dist.length*30);
        g.drawLine(120, 20, 120, dik.dik.dist.length*30);
        
        g.setFont(new Font("Monospaced", Font.BOLD, 22));
        int j=30;
        for(int i=1;i<dik.dik.graph.length;i++) {
            g.setColor(Color.WHITE);
            String s="";
            if(dik.dik.dist[i]==Integer.MAX_VALUE) {
                s=String.valueOf(Character.toChars(Integer.parseInt("221E", 16)));;
            }
            else {
                s=""+dik.dik.dist[i];
            }
            g.drawString(s, 32, j+=30);
        }
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        g.drawString("Distance From Source", 20, dik.dik.dist.length*30+35);
        for(int i=1;i<dik.dik.dist.length;i++) {
            String s="Distance from 1 to "+i+" is ";
            if(dik.dik.dist[i]==Integer.MAX_VALUE) {
                s+=String.valueOf(Character.toChars(Integer.parseInt("221E", 16)));
            }
            else {
                s+=""+dik.dik.dist[i];
            }
            g.drawString(s, 20, dik.dik.dist.length*30+35+(i*25));
        }
    }
    
    public void paintComponent(Graphics g) {
        switch(dik.dr.algoSelected) {
            case 1:
                paintDikstra(g);
                break;
            case 2:
                paintBFS(g);
                break;
            case 3:
                paintDFS(g);
                break;
        }
    }
    
    @Override
    public void run() {
        while(true) {
            repaint();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(pnlMatrix.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
