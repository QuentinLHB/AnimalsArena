package View;

/**
 * Class allowing to store text and display it all at once.
 * Keeps a log of all text buffered (until it is reseted).
 */
public class BufferedText {

    private static String log = "";
    private static String bufferedText = "";
    private static boolean change;

    /**
     * Adds text to display when the {@link #getBufferedText() getBufferedText} method is called.
     * @param text Text to store.
     */
    public static void addBufferedText(String text) {
        bufferedText += text;
        change = true;
    }

    /**
     * Returns the stored text and resets the buffered text.
     * @return String of all text buffered since the last call.
     */
    public static String getBufferedText() {
        String t = bufferedText;
        log += bufferedText;
        resetBufferedText();
        return t;

    }

    /**
     * Resets the buffered text.
     */
    private static void resetBufferedText(){
        bufferedText = "";
        change = false;
    }

    /**
     * Checks if the buffered text has changed since the last usage of {@link #getBufferedText() getBufferedText} method.
     * @return True if it changed, false if it didn't.
     */
    public static boolean hasChanged(){
        return change;
    }

    /**
     * Returns the log of every buffered text since the log got reseted.
     * @return String of all buffered text?
     */
    public static String getLog(){
        return log;
    }

    /**
     * Separates the buffered texts displayed at each turn, announcing the turn in the log.
     * @param turnNumber Number of the current turn.
     */
    public static void addTurnToLog(int turnNumber){
        log += String.format("<br>Turn %d:<br>",turnNumber);
    }

    /**
     * Resets the log.
     */
    public static void resetLog(){
        log = "";
    }

}
