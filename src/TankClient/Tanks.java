package TankClient;

import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tanks {
    int x , y ;
    int xold , yold;
    public static final int DISPLACEMENT = 5; //坦克位移量
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    boolean bL = false,bU = false,bR = false,bD = false;
    public enum direction {L,LU,U,RU,R,RD,D,LD,STOP}
    public direction dir = direction.STOP;
    public direction ptDir = direction.D;

    public boolean isGood() {
        return good;
    }

    private boolean good = true;//敌我识别

    public boolean isTankLive() {
        return tankLive;
    }
    public void setTankLive(boolean tankLive) {
        this.tankLive = tankLive;
    }

    private boolean tankLive = true;
    private Random rn = new Random(); //产生一个随机数
    private int slpe = rn.nextInt(12) + 3;
    TankClients tc ;

    public Tanks(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.xold = x;
        this.yold = y;
        this.good = good;
    }
    public Tanks(int x ,int y,boolean good,direction dir, TankClients tc){ //持有TC的引用
        this(x, y,good);
        this.tc = tc;
        this.dir = dir;
    }

    public void draw(Graphics g){ //画出一辆坦克
        if(!tankLive) {
            if(!good) {
                tc.nemetanks.remove(this);
            }
            return;
        }
        Color c = g.getColor();
        if (good){
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.BLUE);
        }
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
        ptDraw(g);
        move();
    }
    public void ptDraw(Graphics g){ //炮筒
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
        xold = x;
        yold = y;
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
        //出界处理

        if (x <= 0) x = 0;
        if (y <= 30) y =30;
        if (x >= TankClients.WINDOW_WIDTH - Tanks.WIDTH ) x = TankClients.WINDOW_WIDTH - Tanks.WIDTH;
        if (y >= TankClients.WINDOW_HEIGHT - Tanks.HEIGHT ) y = TankClients.WINDOW_HEIGHT - Tanks.HEIGHT;

        if (!good){
            direction [] d = direction.values();
            if (slpe == 0){
                slpe =  rn.nextInt(12) + 3;
                dir = d[rn.nextInt(d.length)];
            }
            slpe--;
            if (rn.nextInt(30) >25){
                this.fire();
            }
        }
    }

    public void stay (){
        x = xold;
        y = yold;
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
    public void fire(){ //
        if (!tankLive)
            return;
        int x = this.x + Tanks.WIDTH/2 -Missile.WIDTH/2;
        int y = this.y + Tanks.HEIGHT/2 - Missile.HEIGHT/2 ;
        Missile m;
        m = new Missile(x,y,ptDir,this.good,this.tc);
        tc.missiles.add(m);
    }
    public Rectangle getRec(){ //碰撞
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }
    public boolean colliedessWithWall(Wall w){ //坦克撞墙
        if (this.tankLive && this.getRec().intersects(w.getRec())){
            stay();
            return true;
        }
        return false;
    }

    public boolean colliedesTanks(List<Tanks> tk){ //坦克相撞
        for (int i = 0; i < tk.size(); i++) {
            Tanks t = tk.get(i);
            if (this.tankLive && t.isTankLive() && this !=t && this.getRec().intersects(t.getRec())){
                stay();
                return true;
            }
        }
        return false;
    }

}
