package TankClient;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClients extends Frame {

    final static int WINDOW_WIDTH = 800;
    final static int WINDOW_HEIGHT = 600;

    int x = 50 , y = 50 ;
    Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,40,40);
        y += 30;
    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(WINDOW_WIDTH,WINDOW_HEIGHT);
        }
        Graphics goffsreen = offScreenImage.getGraphics(); //拿到画笔
        Color c = goffsreen.getColor();
        goffsreen.setColor(Color.green);
        goffsreen.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        goffsreen.setColor(c);
        paint(goffsreen);
        g.drawImage(offScreenImage,0,0,null);//绘制指定图像
    }

    public class PaintTherad implements Runnable{

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
        setVisible(true); //显示窗口
        setResizable(false);//是否可以由用户调节窗口
        new Thread(new PaintTherad()).start();//启动线程
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
