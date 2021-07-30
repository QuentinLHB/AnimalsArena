package game.GUI;

import javax.swing.*;
import java.awt.*;

public class BattleFrame extends JFrame {
    Controler controler;
    
    public BattleFrame(Controler controler){
        this.controler = controler;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(30, 30));

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        //




        // North panel
        JPanel panNorth = new JPanel();
        panNorth.setLayout(new GridLayout()); //todo
        // ...
        contentPanel.add(panNorth, BorderLayout.NORTH);
    }
}
