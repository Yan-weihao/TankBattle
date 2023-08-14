package TankClient;

import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tanks {
    int x , y ;
    public static final int DISPLACEMENT = 5; //坦克位移量
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    boolean bL = false,bU = false,bR = false,bD = false;
    public enum direction {L,LU,U,RU,R,RD,D,LD,STOP}
    public direction dir = direction.STOP;
    public direction ptDir = direction.D;

    TankClients tc ;

    public Tanks(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Tanks(int x ,int y,TankClients tc){ //持有TC的引用
        this(x, y);
        this.tc = tc;
    }

    public void draw(Graphics g){ //画出一辆坦克
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
        ptDraw(g);
        move();
    }
    public void ptDraw(Graphics g){
        switch (ptDir){
            case L -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x,y+Tanks.HEIGHT/2) ;
            case LU -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x,y);
            case U -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x+Tanks.WIDTH/2,y);
            case RU -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x+Tanks.WIDTH,y);
            case R -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x+Tanks.WIDTH,y+Tanks.HEIGHT/2);
            case RD -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x+Tanks.WIDTH,y+Tanks.HEIGHT);
            case D -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x+Tanks.WIDTH/2,y+Tanks.HEIGHT);
            case LD -> g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HEIGHT/2,x,y+Tanks.HEIGHT);
        }
    }

    public void move(){//移动
        switch (dir) {
            case L -> x -= DISPLACEMENT;
            case LU -> {
                x -= DISPLACEMENT;
                y -= DISPLACEMENT;
            }
            case U -> y -= DISPLACEMENT;
            case RU -> {
                x += DISPLACEMENT;
                y -= DISPLACEMENT;
            }
            case R -> x += DISPLACEMENT;
            case RD -> {
                x += DISPLACEMENT;
                y += DISPLACEMENT;
            }
            case D -> y += DISPLACEMENT;
            case LD -> {
                y += DISPLACEMENT;
                x -= DISPLACEMENT;
            }
            case STOP -> {
            }
        }
        if(this.dir != direction.STOP){
            this.ptDir = this.dir;
        }

    }

    public void KeyPressed(KeyEvent e){ //通过键盘控制坦克方向
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT -> bL =true;
            case KeyEvent.VK_UP -> bU = true;
            case KeyEvent.VK_RIGHT -> bR = true;
            case KeyEvent.VK_DOWN -> bD = true;
        }
        locateDirection();
    }


    public void keyReleased(KeyEvent e){ //抬起键盘回复
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL ->  fire();
            case KeyEvent.VK_LEFT -> bL =false;
            case KeyEvent.VK_UP -> bU = false;
            case KeyEvent.VK_RIGHT -> bR = false;
            case KeyEvent.VK_DOWN -> bD = false;
        }
        locateDirection();
    }
    public void locateDirection (){ //确定方向
        if (bL && !bU && !bR && !bD)  dir = direction.L;
        else if (bL && bU && !bR && !bD)  dir = direction.LU;
        else if (!bL && bU && !bR && !bD)  dir = direction.U;
        else if (!bL && bU && bR && !bD)  dir = direction.RU;
        else if (!bL && !bU && bR && !bD)  dir = direction.R;
        else if (!bL && !bU && bR && bD)  dir = direction.RD;
        else if (!bL && !bU && !bR && bD)  dir = direction.D;
        else if (bL && !bU && !bR && bD)  dir = direction.LD;
        else if (!bL && !bU && !bR && !bD)  dir = direction.STOP;
    }
    public void fire(){
        int x = this.x + Tanks.WIDTH/2 -Missile.WIDTH/2;
        int y = this.y + Tanks.HEIGHT/2 - Missile.HEIGHT/2 ;
        Missile m;
        m = new Missile(x,y,ptDir,this.tc);
        tc.missiles.add(m);
    }

}
