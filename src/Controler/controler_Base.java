package Controler;

import Model.Util.Position;
import Model.playerAI.Concrete.Player;

import java.util.ArrayList;

public class controler_Base {

    protected ArrayList<Player> players ;

    protected controler_Base(){
        players = new ArrayList<>();
    }

    public Player getPlayer(Position position) {
        for (Player player: players) {
            if(player.getPosition().equals(position)) return player;
        }
        return null;
    }

    public Player getFoe(Player player) {
        return player.equals(players.get(0)) ? players.get(1):players.get(0);
    }

}
