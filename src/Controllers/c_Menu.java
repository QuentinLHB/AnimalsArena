package Controllers;

import Model.Action.Attack.Concrete.AttackFactory;
import Model.Animal.Behaviors.BehaviorFactory;
import Model.Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Model.Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Model.Animal.Creation.Concrete.*;
import Model.Util.Position;
import Model.Util.Serialization;
import Model.playerAI.Concrete.Player;
import View.Frames.CreationMenuFrame;
import View.Frames.CustomizationMenu;
import View.Frames.MenuFrame;
import View.Frames.PickCustomizedFrame;

import javax.swing.*;
import java.util.ArrayList;

public class c_Menu extends controler_Base{

    ArrayList<Player> getPlayers(){return players;}
    MenuFrame frmMainMenu;
    CreationMenuFrame frmCreationMenu;
    CustomizationMenu frmCustomizationMenu;

//    JFrame currentFrame;
    public c_Menu(){
        frmMainMenu = new MenuFrame(this);
    }
    /**
     * Adds or changes the players.
     * @param p1
     * @param p2
     */
    public void initiatePlayers(Player p1, Player p2){
        if(players.size() >= 2){
            players.set(0, p1);
            players.set(1, p2);
        }
        else {
            players.add(p1);
            players.add(p2);
        }
    }
    public Animal createAnimal(Player player, Player foe, AnimalKind animalKind, String nickname, ElementType... elementType) {
        Animal animal;
        if(nickname.equals("") || nickname == null) animal = AnimalFactory.CreateAnimal(animalKind, elementType);
        else  animal = AnimalFactory.CreateAnimal(animalKind, nickname, elementType);

        addAnimaltoPlayers(animal, player, foe);
        return animal;
    }

    public Animal createCustomAnimal(Player player) {
        Animal animal = new Animal(frmCustomizationMenu.lblNickname.getText(),
                (float)frmCustomizationMenu.sliders.get(StatID.MAX_HEALTH).getValue(),
                (float)frmCustomizationMenu.sliders.get(StatID.ATTACK).getValue()/100,
                (float)frmCustomizationMenu.sliders.get(StatID.DEFENSE).getValue()/100,
                (float)frmCustomizationMenu.sliders.get(StatID.SPEED).getValue()/100
        );

        for (int i = 0; i <frmCustomizationMenu.lstChosenAttacks.getModel().getSize(); i++) {
            AttackFactory.addAttackToAnimal(animal, frmCustomizationMenu.listModel.getElementAt(i));
        }

        BehaviorFactory.addBehaviors(
                animal,
                (AttackBehaviorEnum)frmCustomizationMenu.cboAtkBhv.getSelectedItem(),
                (DefendBehaviorEnum)frmCustomizationMenu.cboDefBhv.getSelectedItem(),
                (DieBehaviorEnum)frmCustomizationMenu.cboDieBhv.getSelectedItem()
                );

        Serialization.addAnimalToSave(animal);
        addAnimaltoPlayers(animal, player, getFoe(player));
        return animal;
    }

    private void addAnimaltoPlayers(Animal animal, Player player, Player foe){
        player.setAlly(animal);
        foe.setFoe(animal);
    }



    public Animal createRandomAnimal(Player player, Player foe){
        Animal animal = AnimalFactory.CreateRandomAnimal();
        player.setAlly(animal);
        foe.setFoe(animal);
        return animal;
    }

    public boolean areAnimalsInitiated(){
        for (Player player: players) {
            if(player.getAlly() == null) return false;
        }
        return true;
    }


    public void closeMainMenu(){
        frmMainMenu.setVisible(false);
    }

    public void reopenMainMenu(){
        if(!frmMainMenu.isVisible()){
            frmMainMenu.setVisible(true);
        }

    }

    public void openBattleFrame() {
        closeMainMenu();
        new c_Battle(this);
    }


    public void openCreationMenuFrame() {
        frmCreationMenu = new CreationMenuFrame(this, frmMainMenu, getPlayer(Position.BOTTOM), getPlayer(Position.TOP));
    }

    public void openCustomizationFrame(Player currentPlayer) {
        new CustomizationMenu(this, frmCreationMenu, currentPlayer);
    }

    public void setCustomizationFrame(CustomizationMenu frame){
        this.frmCustomizationMenu = frame;
    }

    public void openPickCustomizedFrame(JDialog owner, Player player) {
        new PickCustomizedFrame(this, owner, player);
    }

    public Animal[] getSavedAnimals() {
        ArrayList<Animal> savedAnimals = Serialization.loadAnimals();
        Animal[] arrayAnimals = new Animal[savedAnimals.size()];
        arrayAnimals = savedAnimals.toArray(arrayAnimals);
        return arrayAnimals;
    }
}
