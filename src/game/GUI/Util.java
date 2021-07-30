package game.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Util {

    public static JPanel setContentPane(JFrame frame){
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        contentPanel.setLayout(new BorderLayout());
        frame.setContentPane(contentPanel);
        return contentPanel;
    }

    public static void initFrame(JFrame frame, String title, int width, int height){
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void exit(JFrame frame) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
