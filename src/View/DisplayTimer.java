package View;

import View.Frames.BattleFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayTimer extends Timer {
    /**
     * Text to display in the footer label.
     */
    public String footerText;

    /**
     * Member used in the {@link # timer} member to display the text letter by letter.
     * Represents the text that has been displayed so far in the execution of the timer.
     */
    String textSoFar = "<html>";

    public BattleFrame battleFrame;
    private TimerActionListener listener;

    /**
     * Creates a {@code Timer} and initializes both the initial delay and
     * between-event delay to {@code delay} milliseconds. If {@code delay}
     * is less than or equal to zero, the timer fires as soon as it
     * is started. If <code>listener</code> is not <code>null</code>,
     * it's registered as an action listener on the timer.
     *
     * @param delay    milliseconds for the initial and between-event delay
     * @param listener an initial listener; can be <code>null</code>
     * @see #addActionListener
     * @see #setInitialDelay
     * @see #setRepeats
     */
    public DisplayTimer(int delay, TimerActionListener listener, BattleFrame battleFrame) {
        super(delay, listener);
        this.listener = listener;
        listener.setDisplayTimer(this);
        this.battleFrame = battleFrame;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
        start();
    }
}
