package TankClient;

import java.awt.*;

public class Explode {
    int x , y;
    int[] diameter = {4,7,12,18,26,38,49,30,14,6 };
    int step = 0 ;
    private boolean explodeLive = true;
    TankClients tc ;
    public Explode(int x, int y, TankClients tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }
    public void draw(Graphics g){
        if(!explodeLive){
            tc.explodes.remove(this);
            return;
        }
        if (step == diameter.length){
            explodeLive = false;
            step = 0;
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x,y,diameter[step],diameter[step]);
        step++;
    }

}
