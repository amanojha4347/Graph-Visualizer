
package projectnodeslinkdraw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class FrmAnimation extends JPanel implements Runnable{
    JFrame main;
    ProjectNodesLinkDraw dr;
    JPanel pnl;
    pnlMatrix pnlMa;
    Dikstra dik;
    BFS bfs;
    DFS dfs;
    
    public FrmAnimation(ProjectNodesLinkDraw dr) throws InterruptedException {
        this.dr=dr;
        
        switch(dr.algoSelected) {
            case 1:
                dik= new Dikstra(this);
                break;
            case 2:
                bfs= new BFS(this);
                break;
            case 3:
                dfs = new DFS(this);
                break;
        }
        
        init();
        pnlMa=new pnlMatrix(this);
        main.getContentPane().add(pnlMa);
        Thread thrd = new Thread(pnlMa);
        thrd.start();
    }
    
    public void repaint(int millis) throws InterruptedException {
        repaint();
        Thread.sleep(millis);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        switch(dr.algoSelected) {
            case 1:
                dik.paint(g);
                break;
            case 2:
                bfs.paint(g);
                break;
            case 3:
                dfs.paint(g);
                break;
        }
    }

    private void init() {
        this.setPreferredSize(new Dimension(1080, 720));
        main=new JFrame();
        main.setSize(1580, 720);
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        main.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dr.frm.setState(JFrame.NORMAL);
            }
        });
        main.setVisible(true);
        main.setLayout(new BorderLayout());
        main.getContentPane().add(this,BorderLayout.WEST);
    }

    @Override
    public void run() {
        try {
            switch(dr.algoSelected) {
                case 1:
                    dik.dijkstra(dik.graph, 1);
                    break;
                case 2:
                    bfs.BFS(1);
                    break;
                case 3:
                    dfs.DFS2(1,"1");
                    break;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmAnimation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
