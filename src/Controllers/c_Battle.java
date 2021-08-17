package Controllers;

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
import java.net.URL;

public class c_Battle extends controler_Base {

    c_Menu menuControler;
    JFrame battleFrame;

    /**
     * Constructor of the battle controller. Should be called by a {@link c_Menu Menu controller}.
     * @param menuController Menu controller.
     */
    public c_Battle(c_Menu menuController){
        this.menuControler = menuController;
        players = menuController.players;
        BufferedText.resetLog();
        battleFrame = new BattleFrame(this);
    }

    // ************ MODEL GETTERS *****************

    /**
     * Returns the animal playing on the specified {@link Position position}.
     * @param position Position from which to return the animal.
     * @return The {@link IAnimal animal} placed on the position.
     */
    public IAnimal getAnimal(Position position) {
        for (Player player: players) {
            if(player.getPosition().equals(position)) return player.getAlly();
        }
        return null;
    }

    /**
     * Returns the animals' statuses.
     * @param animal Animal to check.
     * @return A string representing the animals' statuses. Format: [PSN] [FEAR]
     */
    public String getStatus(IAnimal animal) {
        StringBuilder statuses = new StringBuilder();

        for (IStatus status :
                animal.getStatuses()) {
            statuses.append(status.getStatusID().initials()).append(" ");
        }
        return statuses.toString();
    }

    /**
     * Returns the player playing on the specified {@link Position position}
     * @param position Position taken by the player, {@link Position#BOTTOM} OR {@link Position#TOP}.
     * @return The {@link Player animal} placed on the position.
     */
    @Override
    public Player getPlayer(Position position) {
        for (Player player: players) {
            if(player.getPosition().equals(position)) return player;
        }
        return null;
    }

    /**
     * Returns the specified player's opponent.
     * @param player Player.
     * @return The player's opponent.
     */
    public Player getOpponent(Player player){
        if(players.size() == 2){
            return getPlayer(player.getOppositePosition());
        }
        return null;
    }

    /**
     * Returns the animal's current HP.
     * @param animal Animal
     * @return a string representing its HP. Format : "80 / 100"
     */
    public String getHP(IAnimal animal){
        return String.format("%d / %d", animal.getHealth(), Math.round(animal.getStat(StatID.MAX_HEALTH)));
    }

    /**
     * Checks if there is new text effects to display.
     * @return True if there is buffered text to display.
     */
    public boolean hasTextToDisplay() {
        return BufferedText.hasChanged();
    }

    /**
     * Returns the text that has been buffered during the execution of the previous move.
     * @return A String of every effect that has to be displayed.
     */
    public String getTextToDisplay() {
        return BufferedText.getBufferedText();
    }

    /**
     * Gets the {@link URL URL} needed to load the animal's sprite into an {@link ImageIcon Image Icon}
     * @param position Position of the animal (top or bottom sprite needed)
     * @return The resource's URL.
     */
    public URL getUrl(Position position) {
        URL url;
        try{
             url = getPlayer(position).getAlly().getAnimalKind().getURL(position);
        }catch (Exception e){
            url = getClass().getResource(String.format("/resources/images/custom_%s.png", position.equals(Position.TOP) ? "top":"bottom"));
        }
        return url;
    }

    // ************** BATTLE EXECUTION **************

    /**
     * Returns the player whose animal is the fastest.
     * @return
     */
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
            if(animal.getStat(StatID.SPEED) > fasterAnimal.getStat(StatID.SPEED)){
                fasterAnimal = animal;
                fasterPlayer = player;
            }
        }
        return fasterPlayer;
    }

    /**
     * Executes the end of turn actions : Statuses effects, ...
     */
    public void turnEnding() {
        for(Player player : players){
            player.getAlly().endOfTurn();
        }
    }

    /**
     * Checks if a player is a bot or a human.
     * @param player Player to check.
     * @return True if its a bot.
     */
    public boolean isBot(Player player){
        return player.isBot();
    }

    /**
     * Verifies if a player can act.
     * @param player Player to check.
     * @return True if the player can act.
     */
    public boolean canPlayerAct(Player player) {
        if(player.getAlly().canAct())return  true;
        else{
            BufferedText.addBufferedText(String.format("%s can't act.", player.getAlly()));
            return false;
        }
    }

    /**
     * Returns an attacked choosed by the AI.
     * @param player Ai to
     * @return
     */
    public IAttack chooseAIMove(PlayerAI player) {
        return player.chooseAttack();
    }

    /**
     * Checks if the game is finished (if an animal is KO).
     * @return True if a player has won..
     */
    public boolean isGameFinished() {
        for(Player player : players){
            if(!player.getAlly().isAlive()) return true;
        }
        return false;
    }

    /**
     * Returns the winning player.
     * @return
     */
    public Player getWinner(){
        if(isGameFinished()){
            for(Player player : players){
                if(!player.getAlly().isAlive()) return getOpponent(player);
            }
        }
        return null;
    }

    /**
     * Executes the end game actions.
     */
    public void endGame() {
        menuControler.reopenMainMenu();
        battleFrame.dispose();

    }

}
