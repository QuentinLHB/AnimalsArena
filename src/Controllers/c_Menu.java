package Controllers;

import Model.Action.Attack.Concrete.AttackEnum;
import Model.Action.Attack.Concrete.AttackFactory;
import Model.Animal.Behaviors.BehaviorFactory;
import Model.Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Model.Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Model.Animal.Creation.Concrete.*;
import Model.Util.Position;
import Model.Util.Serialization;
import Model.playerAI.Concrete.Player;
import View.Frames.*;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;

public class c_Menu extends controler_Base{

    ArrayList<Player> getPlayers(){return players;}
    MenuFrame frmMainMenu;
    CreationMenuFrame frmCreationMenu;
    CustomizationMenu frmCustomizationMenu;

    public c_Menu(){
        setTheme("Dark");
        frmMainMenu = new MenuFrame(this);
    }

    // ************ WINDOW HANDLER ***************

    /**
     * Hides the main menu.
     */
    public void hideMainMenu(){
        frmMainMenu.setVisible(false);
    }

    /**
     * Shows the main menu.
     */
    public void reopenMainMenu(){
        if(!frmMainMenu.isVisible()){
            frmMainMenu.setVisible(true);
        }

    }

    /**
     * Opens the battle frame.
     */
    public void openBattleFrame() {
        hideMainMenu();
        new c_Battle(this);
    }

    /**
     * Open the animal creation menu.
     */
    public void openCreationMenuFrame() {
        frmCreationMenu = new CreationMenuFrame(this, frmMainMenu, getPlayer(Position.BOTTOM), getPlayer(Position.TOP));
    }

    /**
     * Open the animal customization frame.
     * @param currentPlayer Current player.
     */
    public void openCustomizationFrame(Player currentPlayer) {
        new CustomizationMenu(this, frmCreationMenu, currentPlayer);
    }

    /**
     * Sets the customization frame, used to create the animal directly from the frame's objects.<br>
     * Implemented that way to avoid passing too many arguments to create the animal.
     * @param frame Customization Frame.
     */
    public void setCustomizationFrame(CustomizationMenu frame){
        this.frmCustomizationMenu = frame;
    }

    /**
     * Opens the frame showing the existing customized animals.
     * @param owner Parent {@link JDialog JDialog frame}
     * @param player Current player.
     */
    public void openPickCustomizedFrame(JDialog owner, Player player) {
        new PickCustomizedFrame(this, owner, player);
    }

    /**
     * Opens the Information Frame, showing details about the game.
     * @param frame
     */
    public void openInfoFrame(JFrame frame){
        new InfoFrame(this, frame);
    }

    public void openAndFillInfoFrame(JDialog owner, AttackEnum attack) {
        new InfoFrame(this, owner, attack);

    }

    public void openAndFillInfoFrame(JDialog owner, ElementType type) {
        new InfoFrame(this, owner, type);
    }

    public void openAndFillInfoFrame(JDialog owner, AnimalKind animalKind) {
        new InfoFrame(this, owner, animalKind);
    }




    /**
     * Opens the customization frame and loads an animal in it.
     * @param owner Parent {@link JDialog JDialog frame}
     * @param animal Animal to load in the frame.
     * @param currentPlayer Current player.
     */
    public void openEditCustomAnimalFrame(JDialog owner, Animal animal, Player currentPlayer) {
        new CustomizationMenu(this, owner, animal, currentPlayer);
    }

    // ****************** INIT / CREATION **************

    /**
     * Adds or changes the players.
     * @param p1 Player 1
     * @param p2 Player 2
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

    /**
     * Creates a new animal based on informations received in parameters, and adds it to the players.
     * @param player Player whose animal to add
     * @param nickname Nickname the user wants to give, if any.
     * @param animalKind Animal kind chosen by the user.
     * @param elementType Types chosen by the user.
     * @return The created animal.
     */
    public Animal newAnimal(Player player, String nickname, AnimalKind animalKind, ElementType... elementType) {
        Animal animal;
        if(nickname.equals("")) animal = AnimalFactory.CreateAnimal(animalKind, elementType);
        else  animal = AnimalFactory.CreateAnimal(animalKind, nickname, elementType);

        addAnimalToPlayers(player, animal);
        return animal;
    }

    /**
     * Creates a custom animal.
     * <br> /!\ The method directly uses the components of the Customization form. The form must initiated and active.
     * @param player Player for whom to create an animal.
     * @return The created animal.
     */
    public Animal validateCustomAnimal(Player player, Animal oldAnimal) {
        if(oldAnimal != null)
            Serialization.removeAnimalFromSave(oldAnimal);
        Animal animal = createCustomAnimal();
        if(player != null)
            addAnimalToPlayers(player, animal);
        return animal;
    }

    /**
     * Creates an animal using the frame's objects.
     * @return
     */
    private Animal createCustomAnimal(){
        Animal animal;
        try{

            animal = new Animal(frmCustomizationMenu.txtName.getText(),
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
            return animal;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adds a new animal to the players.
     * @param player Player
     * @param animal
     */
    public void newAnimal(Player player,Animal animal){
        addAnimalToPlayers(player, animal);
    }

    /**
     * Adds an animal to a player and to the opponent (a player registering both ally and foe animals).
     * @param player Player
     * @param animal Player's animal.
     */
    private void addAnimalToPlayers(Player player, Animal animal){
        player.setAlly(animal);
        getFoe(player).setFoe(animal);
    }

    /**
     * Adds a random animal to the player (and its opponent).
     * @param player Player
     * @return The created animal.
     */
    public Animal createRandomAnimal(Player player){
        Animal animal = AnimalFactory.CreateRandomAnimal();
        player.setAlly(animal);
        getFoe(player).setFoe(animal);
        return animal;
    }

    /**
     * Verifies if an animal has been set for the designated player.
     * @param player Player to check for an animal.
     * @return True if an animal is found.
     */
    public boolean isAnimalInitialized(Player player) {
        return (player.getAlly() != null);
    }

    /**
     * Verifies if an animal has been set for every player.
     * @return True if every player has an animal.
     */
    public boolean areAnimalsInitiated(){
        for (Player player: players) {
            if(!isAnimalInitialized(player)) return false;
        }
        return true;
    }

    // ************** OTHER ***********

    /**
     * Returns a list of saved animals.
     * @return
     */
    public Animal[] getSavedAnimals() {
        ArrayList<Animal> savedAnimals = Serialization.loadAnimals();
        Animal[] arrayAnimals = new Animal[savedAnimals.size()];
        arrayAnimals = savedAnimals.toArray(arrayAnimals);
        return arrayAnimals;
    }

    /**
     * Deletes a custom animal from the save file.
     * @param animalToDelete Animal to delete.
     */
    public void deleteAnimal(Animal animalToDelete) {
        Serialization.removeAnimalFromSave(animalToDelete);
    }

    /**
     * Sets the application's theme (look and feel)
     * @param theme "Dark" or "Light".
     */
    public void setTheme(String theme) {
        controler_Base.theme = theme;
        LookAndFeel laf;
        switch (theme){
            case "Dark" -> laf = new FlatDarculaLaf();
            case "Light" -> laf = new FlatLightLaf();
            default -> laf = new FlatIntelliJLaf();
        }

        try {
            UIManager.setLookAndFeel(laf);
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

    }

    /**
     * Gets the URL of the facing animal sprite.
     * @param animalKind Animal whose URL must be retrieved.
     * @return The animal's sprite URL. if it's not found, returns the "Missingno" placeholder.
     */
    public URL getUrl(AnimalKind animalKind) {
        URL url = animalKind.getURL();
        if(url == null ) url = getClass().getResource("/resources/images/missingno.png");
        return url;
    }


}
