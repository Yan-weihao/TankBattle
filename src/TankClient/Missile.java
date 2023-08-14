package TankClient;

import java.awt.*;

public class Missile {
    public static final int DISPLACEMENT = 10 ;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    int x , y ;
    Tanks.direction dir;
    public Missile(int x , int y ,Tanks.direction dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
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
    }
}
