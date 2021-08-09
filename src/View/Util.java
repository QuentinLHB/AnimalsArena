package View;

import Model.Animal.Creation.Abstract.IAnimal;

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

    public static void exit(Window frame) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void hide(JFrame frame) {
        frame.setVisible(false);
    }

    public static String toHtml(String plainText){
        return "<html>" + plainText.replace(".", ".<br>") + "</html>";
    }

    /**
     * Creates a GridBagConstraint with common property :
     * @param column position in the columns (1 : column 1).
     * @param row position in the rows (1 : row 1).
     * @return GridBagConstraint.
     */
    public static GridBagConstraints setGridBagConstraints(int column, int row, double weightClmn, double weightRow){
        var gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(5, 10, 5, 10);
        gc.ipady = 5;
        gc.ipadx = 5;
        gc.weightx = weightClmn;
        gc.weighty = weightRow;

        gc.anchor = GridBagConstraints.NORTH;
        gc.gridx = column;
        gc.gridy = row;

        return gc;
    }


    public static void printCreationConfirmation(IAnimal animal) {
        JOptionPane.showMessageDialog(null, String.format("New animal created : %s", animal.getName()));
    }
}
