package View;

import javax.swing.*;
import java.awt.*;

public class LogFrame extends JDialog {

    public LogFrame(){
        Util.initFrame(this, "Event log", 300, 300);
        JPanel contentPanel = Util.setContentPane(this);

        // Log display
        JLabel lblLog = new JLabel(Util.toHtml(BufferedText.getLog()));
        JScrollPane scrollPane = new JScrollPane(lblLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        // OK Button
        JButton btnOK = new JButton("OK");
        contentPanel.add(btnOK, BorderLayout.SOUTH);
        btnOK.addActionListener(e-> Util.exit(this));

    }

}
