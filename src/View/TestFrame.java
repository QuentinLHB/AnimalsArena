package View;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {

    public TestFrame(){
        var contentpane = getContentPane();



        var url = getClass().getResource("/resources/images/clam.png");
        ImageIcon imageIcon = new ImageIcon(url);

        var labelTest = new JLabel(imageIcon);
        contentpane.add(labelTest);

        Util.initFrame(this, "test", 700, 700);
    }
}
