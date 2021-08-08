package View;

public class BufferedText {

    private static String log = "";
    private static String bufferedText = "";
    private static boolean change;

    public static void addBufferedText(String text) {
        bufferedText += text;
        change = true;
    }

    public static String getBufferedText() {
        String t = bufferedText;
        log += bufferedText;
        resetBufferedText();
        return t;

    }

    private static void resetBufferedText(){
        bufferedText = "";
        change = false;
    }

    public static boolean hasChanged(){
        return change;
    }

    public static String getLog(){
        return log;
    }

    public static void addTurnToLog(int turnNumber){
        log += String.format("<br>Turn %d:<br>",turnNumber);
    }

}
