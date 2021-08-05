package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Util {

    private static String bufferedText;

    public static JPanel setContentPane(JFrame frame){
        JPanel contentPanel = createPanel();
        frame.setContentPane(contentPanel);
        return contentPanel;
    }

    public static JPanel setContentPane(JDialog frame){
        JPanel contentPanel = createPanel();
        frame.setContentPane(contentPanel);
        return contentPanel;
    }

    private static JPanel createPanel(){
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        contentPanel.setLayout(new BorderLayout());
        return contentPanel;
    }

    public static void initFrame(JFrame frame, String title, int width, int height){
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void initFrame(JDialog frame, String title, int width, int height){
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void exit(JFrame frame) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void exit(JDialog frame) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static String toHtml(String plainText){
        return "<html>" + plainText + "</html>";
    }


}
