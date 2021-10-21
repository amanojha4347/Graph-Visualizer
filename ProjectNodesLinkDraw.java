package projectnodeslinkdraw;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;


public class ProjectNodesLinkDraw extends JPanel implements MouseListener,MouseMotionListener{
    JFrame frm;
    int stx=0,sty=0;
    boolean flag=false;
    int idsel=-1;
    int idsel2=-1;
    int id_count=1;
    int id_count2=1;
    int algoSelected;
    ProjectNodesLinkDraw pr=this;
    Timer tim;
    JButton btnMove,btnStart;
    ArrayList<Ball> balls=new ArrayList<>();
    ArrayList<Edge> edges=new ArrayList<>();
    JRadioButton rbtdik = new JRadioButton("Dijkstra");
    JRadioButton rbtdfs = new JRadioButton("DFS Algorithm");
    JRadioButton rbtbfs = new JRadioButton("BFS Algorithm");
    ButtonGroup grp1= new ButtonGroup();
    
    public ProjectNodesLinkDraw() {
        init();
        algoSelected=1;
        tim=new Timer(7000,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<edges.size();i++) {
                    if(i==edges.size()) {
                        break;
                    }
                    if(!edges.get(i).complete){
                        edges.remove(i);
                        repaint();
                    }
                }
            }
        });
        tim.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
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
        g.setColor(Color.RED);
        for(Ball x:balls) {
            g.setColor(Color.RED);
            g.fillOval(x.x-15, x.y-15, 30, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospaced", Font.BOLD, 14));
            g.drawString(""+x.id, x.x-4, x.y+5);
        }
        g.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(3));
        for(Edge edge:edges) 
            g2.drawLine(edge.x1, edge.y1,edge.x2 , edge.y2);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        double x,y;
        x=e.getX();
        y=e.getY();
        x-=7;
        y-=28;
        if((x%100<=15 || x%100>=85) && (y%100<=15 || y%100>=85)) {
            x/=100;
            y/=100;
            int x1=(int)Math.round(x);
            int y1=(int)Math.round(y);
            balls.add(new Ball(x1*100,y1*100,id_count++));
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        
        if(flag) {
            for(Ball a:balls) {
                if(x>=a.x && x<=a.x+60 && y>=a.y && y<=a.y+60) {
                    idsel2=a.id;
                }
            }
        }
        else {
            for(Ball a:balls) {
                if(x>=a.x && x<=a.x+60 && y>=a.y && y<=a.y+60) {
                    stx=a.x;
                    sty=a.y;
                    a.edidstart.add(id_count2);
                    edges.add(new Edge(stx,sty,stx,sty,id_count2++,a.id));
                    idsel=a.id;
                    repaint();
                    break;
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        x-=7;
        y-=28;
        if(flag && idsel2!=-1) {
            for(Ball a:balls) {
                if(a.id==idsel2) {
                    a.x=x;
                    a.y=y;
                    for(int cc:a.edidend) {
                        for(Edge ss:edges) {
                            if(ss.id==cc) {
                                ss.x2=x;
                                ss.y2=y;
                                break;
                            }
                        }
                    }
                    for(int cc:a.edidstart) {
                        for(Edge ss:edges) {
                            if(ss.id==cc) {
                                ss.x1=x;
                                ss.y1=y;
                                break;
                            }
                        }
                    }
                    repaint();
                    break;
                }
            }
        }
        else {
            if(idsel!=-1) {
                for(Edge a:edges) {
                    if(a.id==id_count2-1) {
                        a.x2=x;
                        a.y2=y;
                        repaint();
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        if(Math.abs(x-stx)>0 && Math.abs(sty-y)>0) {
            if(flag) {
                for(Ball a:balls) {
                    if(a.id==idsel2) {
                        double x1=(double)x/100;
                        double y1=(double)y/100;
                        x1=Math.round(x1);
                        y1=Math.round(y1);
                        x1*=100;
                        y1*=100;
                        a.x=(int)x1;
                        a.y=(int)y1;
                        for(int cc:a.edidend) {
                            for(Edge ss:edges) {
                                if(ss.id==cc) {
                                    ss.x2=(int)x1;
                                    ss.y2=(int)y1;
                                    break;
                                }
                            }
                        }
                        for(int cc:a.edidstart) {
                            for(Edge ss:edges) {
                                if(ss.id==cc) {
                                    ss.x1=(int)x1;
                                    ss.y1=(int)y1;
                                    break;
                                }
                            }
                        }
                        idsel2=-1;
                        repaint();
                        break;
                    }
                }
            }
            else {
                for(Ball a:balls) {
                    if(a.id!=idsel && idsel!=-1 && x>=a.x && x<=a.x+60 && y>=a.y && y<=a.y+60) {
                        for(Edge ed:edges) {
                            if(ed.id==id_count2-1) {
                                ed.complete=true;
                                ed.x2=a.x;
                                ed.y2=a.y;
                                ed.ballfin=a.id;
                                a.edidend.add(ed.id);
                                int cost=Integer.parseInt(JOptionPane.showInputDialog("Enter Weight"));
                                ed.cost=cost;
                            }
                        }
                        idsel=-1;
                        repaint();
                        break;
                    }
                }
            }
        } 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    
    }
    
    public void init() {
        grp1.add(rbtdik);
        grp1.add(rbtdfs);
        grp1.add(rbtbfs);
        rbtbfs.setBackground(Color.darkGray);
        rbtdfs.setBackground(Color.darkGray);
        rbtdik.setBackground(Color.darkGray);
        rbtdfs.setForeground(Color.WHITE);
        rbtbfs.setForeground(Color.WHITE);
        rbtdik.setForeground(Color.WHITE);
        rbtdik.setSelected(true);
        btnMove= new JButton("Move");
        btnStart = new JButton("Start");
        JPanel pnl= new JPanel();
        frm=new JFrame();
        frm.getContentPane().setBackground(Color.gray);
        this.setPreferredSize(new Dimension(1080,720));
        frm.setSize(1580,720);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLayout(new BorderLayout());
        frm.getContentPane().add(this,BorderLayout.WEST);
        pnl.add(btnMove);
        pnl.add(btnStart);
        JPanel pnl2= new JPanel();
        pnl2.add(rbtdik);
        pnl2.add(rbtbfs);
        pnl2.add(rbtdfs);
        pnl2.setBackground(Color.darkGray);
        pnl.setBackground(Color.darkGray);
        frm.getContentPane().add(pnl,BorderLayout.EAST);
        frm.getContentPane().add(pnl2);
        frm.addMouseListener(this);
        frm.addMouseMotionListener(this);
        
        btnMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flag) {
                    btnMove.setText("Move");
                    flag=false;
                }
                else {
                    btnMove.setText("Not Move");
                    flag=true;
                }
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmAnimation dik;
                try {
                    if(rbtbfs.isSelected()) {
                        algoSelected=2;
                    }
                    else if(rbtdik.isSelected()) {
                        algoSelected=1;
                    }
                    else {
                        algoSelected=3;
                    }
                    dik = new FrmAnimation(pr);
                    Thread thrd = new Thread(dik);
                    frm.setState(JFrame.ICONIFIED);
                    thrd.start();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProjectNodesLinkDraw.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    static class Ball{
        int x;
        int y;
        int id;
        boolean source=false;
        boolean partially=false;
        boolean visited=false;
        ArrayList<Integer> edidstart=new ArrayList<>();
        ArrayList<Integer> edidend=new ArrayList<>();
        
        public Ball(int x,int y,int id) {
            this.x=x;
            this.y=y;
            this.id=id;
        }
    }
    static class Edge{
        int x1;
        int y1;
        int x2;
        int y2;
        int id;
        int cost =0;
        int ballin;
        int ballfin;
        boolean selected;
        boolean complete=false;

        public Edge(int x1, int y1, int x2, int y2,int id,int ballin) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.id=id;
            this.ballin=ballin;
        }
    }
}
