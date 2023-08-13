package TankClient;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClients extends Frame {
    public void ShowWindow(){
        this.setLocation(400,300);//相对于原地的位置
        this.setSize(800,600);
        setVisible(true); //显示窗口
        setResizable(false);//是否可以由用户调节窗口
    }

    public void OffWindow(){
        this.addWindowListener(new WindowAdapter() {//使用一个匿名类相应关闭窗口事件
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        TankClients  t = new TankClients();
        t.ShowWindow();
        t.OffWindow();
    }




}
