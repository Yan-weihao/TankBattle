package TankClient;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tanks {
    int x , y ;

    public Tanks(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){ //画出一辆坦克
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,40,40);
    }

    public void KeyPressed(KeyEvent e){ //通过键盘控制坦克移动
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT -> x -= 5;
            case KeyEvent.VK_UP -> y -= 5;
            case KeyEvent.VK_RIGHT -> x += 5;
            case KeyEvent.VK_DOWN -> y += 5;
        }
    }
}
