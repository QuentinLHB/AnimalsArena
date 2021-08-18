package Controllers;

import Model.Util.Position;
import Model.playerAI.Concrete.Player;

import java.util.ArrayList;

public abstract class controler_Base {

    protected ArrayList<Player> players ;
    protected static String theme;

    protected controler_Base(){
        players = new ArrayList<>();
    }

    /**
     * Returns the player set in the designated position.
     * @param position Position taken by the player, {@link Position#BOTTOM} OR {@link Position#TOP}.
     * @return Player set in the position passed as parameter, or null if none is set on this position.
     */
    public Player getPlayer(Position position) {
        for (Player player: players) {
            if(player.getPosition().equals(position)) return player;
        }
        return null;
    }

    /**
     * Returns the opposing Player of the player passed as a parameter.
     * @param player Player whose foe must be returned.
     * @return Player's foe.
     */
    public Player getFoe(Player player) {
        return player.equals(players.get(0)) ? players.get(1):players.get(0);
    }

    /**
     * Returns the theme.
     * @return "Dark" or "Light" depending on the current theme.
     */
    public String getTheme(){
        return theme;
    }

}
