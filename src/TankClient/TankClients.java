package TankClient;

import javax.swing.plaf.SplitPaneUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClients extends Frame {

    public final static int WINDOW_WIDTH = 800;
    public final static int WINDOW_HEIGHT = 600;
    Tanks mytanks = new Tanks(50 , 50 , true,this);
    Tanks nemetanks = new Tanks(100,50,false,this);
    Missile mymissile ;
    List<Missile> missiles = new ArrayList<>();
    Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {
        g.drawString("missiles count:" + missiles.size(), 10, 50);
        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.draw(g);
        }
        nemetanks.draw(g);
        mytanks.draw(g);
    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(WINDOW_WIDTH,WINDOW_HEIGHT);
        }
        Graphics goffsreen = offScreenImage.getGraphics(); //拿到画笔
        Color c = goffsreen.getColor();
        goffsreen.setColor(Color.LIGHT_GRAY);
        goffsreen.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        goffsreen.setColor(c);
        paint(goffsreen);
        g.drawImage(offScreenImage,0,0,null);//绘制指定图像
    }

    public class PaintThread implements Runnable{
        //创建一个线程进行重画
        @Override
        public void run() {
            while (true){
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class KeyMonitor extends KeyAdapter{ //监听键盘的事件
        @Override
        public void keyPressed(KeyEvent e) {//按下
            mytanks.KeyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e) {//释放
            mytanks.keyReleased(e);
        }
    }

    public static void main(String[] args) {
        TankClients  t = new TankClients();
        t.ShowWindow();
        t.OffWindow();
    }
    public void ShowWindow(){
        this.setLocation(400,300);//相对于原地的位置
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setTitle("TankBattle");
        this.setBackground(Color.green);
        this.addKeyListener(new KeyMonitor());//添加一个键盘监听
        setVisible(true); //显示窗口
        setResizable(false);//是否可以由用户调节窗口
        new Thread(new PaintThread()).start();//启动线程
    }

    public void OffWindow(){
        this.addWindowListener(new WindowAdapter() {//使用一个匿名类相应关闭窗口事件
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}
