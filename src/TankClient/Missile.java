package TankClient;

import java.awt.*;

public class Missile {
    public static final int DISPLACEMENT = 10 ;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private boolean live = true;

    int x , y ;
    Tanks.direction dir;
    TankClients tc;
    public Missile(int x , int y ,Tanks.direction dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public Missile(int x, int y ,Tanks.direction dir ,TankClients tc){
        this(x,y,dir);
        this.tc = tc;
    }

    public void draw(Graphics g){ //画出炮弹
        Color c = g.getColor();
        g.setColor(Color.black);
        g.fillOval(x,y,WIDTH,HEIGHT);
        move();
    }
    public void move (){
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
        if (x < 0 || y < 0 || y > TankClients.WINDOW_WIDTH || x >TankClients.WINDOW_HEIGHT){
            live =false;
            tc.missiles.remove(this);
        }
    }
    public boolean isLive() {
        return live;
    }
}
