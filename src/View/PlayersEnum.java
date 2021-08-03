package View;

public enum PlayersEnum {
    playerA("Player 1", false),
    playerB("Player 2", false),
    CPUA("CPU 1", true),
    CPUB("CPU 2", true);

    PlayersEnum(String name, boolean bot){
        this.name = name;
        this.bot = bot;
    }

    private final String name;
    private final boolean bot;

    @Override
    public String toString() {
        return name;
    }

    public boolean isBot(){
        return bot;
    }
}
