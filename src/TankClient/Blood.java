package TankClient;

import java.awt.*;

public class Blood {
    int x  , y;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    int step  = 0 ;

    private boolean live = true;
    private int[][] pos = {
            {350, 300}, {360, 300}, {375, 275}, {400, 200}, {360, 270}, {365, 290}, {340, 280}
    };

    public Blood (){
        this.x = pos[0][0];
        this.y = pos[0][1];
    }

    public void draw(Graphics g){
        if (!live) return;
        Color c = g.getColor();
        g.setColor(Color.green);
        g.fillRect(x,y,WIDTH,HEIGHT);
        g.setColor(c);
        move();
    }
    public void move(){
        step++;
        if (step == pos.length){
            step = 0;
        }
            this.x = pos[step][0];
            this.y = pos[step][1];
    }

    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }
    public boolean eta (Tanks tk){
        if (tk.isTankLive()&& this.live && getRect().intersects(tk.getRec())){
            tk.setLife(100);
            this.live = false;
            return true;
        }
        return false;
    }
}
