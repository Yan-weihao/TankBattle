package TankClient;

import java.awt.*;

public class TankClients extends Frame {
    public void ShowWindow(){
        this.setLocation(400,300);//相对于原地的位置
        this.setSize(800,600);
        setVisible(true); //显示窗口
    }

    public static void main(String[] args) {
        TankClients  t = new TankClients();
        t.ShowWindow();
    }
}
