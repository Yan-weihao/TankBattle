package TankClient;

import java.awt.*;
import java.util.List;

public class Missile {
    public static final int DISPLACEMENT = 10 ;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private boolean live = true;
    private boolean good ;

    int x , y ;
    Tanks.direction dir;
    TankClients tc;
    public Missile(int x , int y ,Tanks.direction dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public Missile(int x, int y ,Tanks.direction dir ,boolean good,TankClients tc){
        this(x,y,dir);
        this.tc = tc;
        this.good = good;
    }

    public void draw(Graphics g){ //画出炮弹
        if (!live){
            tc.missiles.remove(this);
            return;
        }
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
        if (x < 0 || y < 0 || x > TankClients.WINDOW_WIDTH || y > TankClients.WINDOW_HEIGHT){
            live =false;
        }
    }

    public boolean hitTank(Tanks tk){ //打中坦克
        if (this.live && this.getRec().intersects(tk.getRec()) && tk.isTankLive() && (tk.isGood() != good)){
             tk.setTankLive(false);
             this.live =false;
             tc.explodes.add(new Explode(x,y,tc));//爆炸模拟位置
             return true;
        }
        return false;
    }

    public boolean hitTanks(List<Tanks> tk){
        for (int i = 0; i < tk.size(); i++) {
            if (hitTank(tk.get(i))){
                return true;
            }
        }
        return false;
    }

    public boolean hitWall(Wall w){
        if (this.live &&this.getRec().intersects(w.getRec()) ){
            this.live = false;
            return true;
        }
        return false;
    }

    public Rectangle getRec(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }
    public boolean isLive() {
        return live;
    }
}
