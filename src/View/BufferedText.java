package View;

public class BufferedText {

    private static String bufferedText = "";
    private static boolean change;

    public static void addBufferedText(String text) {
        bufferedText += text;
        change = true;
    }

    public static String getBufferedText() {
        String t = bufferedText;
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


}
