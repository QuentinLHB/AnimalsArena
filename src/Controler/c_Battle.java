package Controler;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;
import Model.playerAI.Concrete.Player;
import Model.Util.Position;
import Model.playerAI.Concrete.PlayerAI;
import View.Frames.BattleFrame;
import View.BufferedText;

import javax.swing.*;

public class c_Battle extends controler_Base {

    c_Menu menuControler;
    JFrame battleFrame;


    public c_Battle(c_Menu menuControler){
        this.menuControler = menuControler;
        players = menuControler.players;
        battleFrame = new BattleFrame(this);
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

    public Player getOpponent(Player player){
        if(players.size() == 2){
            return getPlayer(player.getOppositePosition());
        }
        return null;
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

    public void turnEnding() {
        for(Player player : players){
            player.getAlly().endOfTurn();
        }
    }

    public boolean isGameFinished() {
        for(Player player : players){
            if(!player.getAlly().isAlive()) return true;
        }
        return false;
    }

    public Player getWinner(){
        if(isGameFinished()){
            for(Player player : players){
                if(!player.getAlly().isAlive()) return getOpponent(player);
            }
        }
        return null;
    }

    public String getTextToDisplay() {
        return BufferedText.getBufferedText();
    }

    public boolean canPlayerAct(Player player) {
        if(player.getAlly().canAct())return  true;
        else{
            BufferedText.addBufferedText(String.format("%s can't act.", player.getAlly()));
            return false;
        }
    }

    public boolean hasTextToDisplay() {
        return BufferedText.hasChanged();
    }

    public IAttack chooseAIMove(Player player) {
        PlayerAI AI;
        try{
            AI = (PlayerAI)player;
        }catch (Exception e){
            e.printStackTrace();
            return player.getAlly().chooseAttack(0);
        }
        return AI.chooseAttack();
    }

    public boolean isBot(Player player){
        return player.isBot();
    }

    public String getStatus(IAnimal animal) {
        String statuses = "";

        for (IStatus status :
                animal.getStatuses()) {
            statuses += status.getStatusID().initials() + " ";
        }
        return statuses;
    }

    public void endGame() {
        menuControler.reopenMainMenu();
        battleFrame.dispose();

    }
}
