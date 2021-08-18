package View.Frames;

import View.BufferedText;
import View.Util;

import javax.swing.*;
import java.awt.*;

/**
 * Frame showing the log information : All text that have been displayed before.
 */
public class LogFrame extends JDialog {

    public LogFrame(JFrame owner){
        super(owner, true);

        JPanel contentPanel = Util.setContentPane(this);

        // Log display
        JLabel lblLog = new JLabel(Util.toHtml(BufferedText.getLog()));
        JScrollPane scrollPane = new JScrollPane(lblLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        // OK Button
        JButton btnOK = new JButton("OK");
        contentPanel.add(btnOK, BorderLayout.SOUTH);
        btnOK.addActionListener(e-> Util.closeFrame(this));

        Util.initFrame(this, "Event log", 300, 300);
    }

}
