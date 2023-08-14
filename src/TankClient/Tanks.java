package TankClient;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tanks {
    int x , y ;
    public static final int DISPLACEMENT = 5; //坦克位移量
    boolean bL = false,bU = false,bR = false,bD = false;
    public enum direction {L,LU,U,RU,R,RD,D,LD,STOP}
    public direction dir = direction.STOP;

    public Tanks(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){ //画出一辆坦克
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,40,40);
        move();
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


    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
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

}
