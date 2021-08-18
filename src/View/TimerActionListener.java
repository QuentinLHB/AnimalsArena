package View;

import View.Frames.BattleFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerActionListener implements ActionListener {


    int i = 0;
    private DisplayTimer displayTimer;
    public String textSoFar = "<html>";

    public TimerActionListener() {

    }

    public void setDisplayTimer(DisplayTimer displayTimer) {
        this.displayTimer = displayTimer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BattleFrame battleFrame = displayTimer.battleFrame;
        String text = displayTimer.footerText;

        JLabel label = battleFrame.lblFooter;
        char[] character = text.toCharArray();
        int arrayNumber = character.length;

        String s = String.valueOf(character[i]);
        textSoFar += s;
        if (s.equals(".") && i < character.length - 3) { // 2nd instruction is meant to avoid having a carriage return on the last period.
            textSoFar += "<br>";
        }
        label.setText(textSoFar + "</html>");
        i++;
        if (i == arrayNumber) {
            i = 0;
            Util.waitOneSec();
            label.setText("");
            displayTimer.stop();
            textSoFar = "<html>";
            battleFrame.resume();
        }
    }
}
