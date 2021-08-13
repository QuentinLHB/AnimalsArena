package View.Frames;
import Controllers.c_Menu;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Util.Serialization;
import Model.playerAI.Concrete.Player;
import View.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreationMenuFrame extends JDialog {
    /**
     * Controler of the frame.
     */
    private c_Menu controler;

    /**
     * Player currently picking an animal creation method.
     */
    private Player currentPlayer;

    /**
     * Second player having to choose an animal.
     */
    private final Player p2;

    /**
     * Title label, printing the current player and a description.
     */
    private JLabel lblTitle;

    /**
     * Button opening a list of customized animals. Only enabled when there are animals to display.
     */
    private JButton btnPickCustomized;


    /**
     * Creates the method creation dialog frame (blocks usage of the owner frame)
     * @param controler Controler.
     * @param owner Parent frame, which can't be accessed as long as the creation is open.
     */
    public CreationMenuFrame(c_Menu controler, JFrame owner, Player p1, Player p2){
        super(owner, true);
        this.controler = controler;
        this.p2 = p2;
        currentPlayer = p1;
        initComponents();
        Util.initFrame(this, "Selection Menu", 400, 500);
    }

    /**
     * Initializes the frame's components.
     */
    private void initComponents() {
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // Label on top
        lblTitle = new JLabel("", SwingConstants.CENTER);
        updateLblTitle();
        lblTitle.setBorder(new EmptyBorder(0,0,20,0));
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        // Buttons
        JPanel panButtons = new JPanel(new GridLayout(4, 1, 10, 10));
        initButtons(panButtons);
        contentPanel.add(panButtons, BorderLayout.CENTER);

    }

    /**
     * Initializes buttons and events.
     */
    private void initButtons(JPanel panButtons) {

        JButton btnExistingPick = new JButton("<html>Select a standard animal</html>");
        panButtons.add(btnExistingPick);
        btnExistingPick.addActionListener(this::btnPickExistingAnimal_click);

        JButton btnRandomPick = new JButton("<html>Generate a random animal</html>");
        panButtons.add(btnRandomPick);
        btnRandomPick.addActionListener(this::btnRandomPick_click);

        JButton btnCustomize = new JButton("<html>Customize your own animal and stats</html>");
        panButtons.add(btnCustomize);
        btnCustomize.addActionListener(this:: btnCustomize_click);

        btnPickCustomized = new JButton("<html>Choose one of the customized animals</html>");
        panButtons.add(btnPickCustomized);
        btnPickCustomized.addActionListener(this::btnPickCustomized_click);
        tryEnablePickCustomButton();
    }

    /**
     * Opens a customized animals selection frame.
     */
    private void btnPickCustomized_click(ActionEvent e) {
        controler.openPickCustomizedFrame(this, currentPlayer);
        endPickOption();
    }

    /**
     * When the button is clicked, opens a frame allowing to choose an existing animal, type, etc.
     */
    private void btnPickExistingAnimal_click(ActionEvent e){
        new NewAnimalFrame(controler, this, currentPlayer);
        endPickOption();
    }

    /**
     * When the button is clicked, randomly picks an existing animal and type(s).
     */
    private void btnRandomPick_click(ActionEvent e){
        IAnimal animal = controler.createRandomAnimal(currentPlayer, controler.getFoe(currentPlayer));
        Util.printCreationConfirmation(animal);
        endPickOption();
    }

    /**
     * When the button is clicked, opens a frame allowing to customize an animal (stats, attacks...)
     */
    private void btnCustomize_click(ActionEvent e){
        controler.openCustomizationFrame(currentPlayer);

        endPickOption();
    }

    /**
     * Updates the title label with the current player.
     */
    private void updateLblTitle() {
        String title = String.format("<html>How will <b>%s</b> fight ?</html>", currentPlayer);
        lblTitle.setText(title);
    }

    /**
     * After an animal has been picked/created, asks the second player to choose from the same options,
     * or closes the window if the second player already picked its animal.
     */
    private void endPickOption() {
        tryEnablePickCustomButton();
        // Same player if they haven't picked an animal.
        if(!controler.isAnimalInitialized(currentPlayer)){
            return;
        }

        // Back to menu to open the battle frame when both players have picked their animal
        if (currentPlayer.equals(p2)) {
            Util.closeFrame(this);
        }
        // When 1st player has chosen, switches to the 2nd player
        else{
            currentPlayer = p2;
            updateLblTitle();
        }
    }

    /**
     * If there are custom animals to display, enable the according button.
     */
    private void tryEnablePickCustomButton() {
        btnPickCustomized.setEnabled(!Serialization.isSaveEmpty());
    }

}
