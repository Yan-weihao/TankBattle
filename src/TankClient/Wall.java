package TankClient;

import java.awt.*;
import java.util.Random;


public class Wall {
    TankClients tc;
    int x , y, w ,h;

    public Wall(TankClients tc, int x, int y, int w, int h) {
        this.tc = tc;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.GREEN);
        g.fillRect(x,y,w,h);
        g.setColor(c);
    }
    public Rectangle getRec(){
        return new Rectangle(x, y, w, h) ;
    }

}
