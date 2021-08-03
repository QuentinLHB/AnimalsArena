package Controler;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;
import Model.playerAI.Concrete.Player;
import Model.Util.Position;

import java.util.ArrayList;

public class c_Battle {

    ArrayList<Player> players;


    public c_Battle(c_Menu menuControler){
        players = menuControler.getPlayers();
    }

    public String getHP(IAnimal animal){
        return String.format("%d / %d", animal.getHealth(), Math.round(animal.getMaxHealth()*animal.getStatAlteration(StatID.MAX_HEALTH)));
    }

    public IAnimal getAnimal(Position position) {
        for (Player player: players) {
            if(player.getPosition().equals(position)) return player.getAlly();
        }
        return null;
    }

    public Player getPlayer(Position position) {
        for (Player player: players) {
            if(player.getPosition().equals(position)) return player;
        }
        return null;
    }

    public void executeAttack(IAttack attack, IAnimal animalB) {
        //...
    }

    public Player whichIsFaster() {
        IAnimal fasterAnimal;
        Player fasterPlayer;
        try{
            fasterPlayer = players.get(0);
            fasterAnimal = players.get(0).getAlly();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        for(Player player : players){
            IAnimal animal = player.getAlly();
            if(animal.getStat(StatID.SPEED)*animal.getStatAlteration(StatID.SPEED)
                    > fasterAnimal.getStat(StatID.SPEED)*fasterAnimal.getStatAlteration(StatID.SPEED)){
                fasterAnimal = animal;
                fasterPlayer = player;
            }
        }
        return fasterPlayer;
    }
}
