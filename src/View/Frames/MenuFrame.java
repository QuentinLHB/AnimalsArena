package View.Frames;
import Controllers.c_Menu;
import Model.playerAI.Concrete.Player;
import Model.playerAI.Concrete.PlayerAI;
import Model.Util.Position;
import View.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuFrame extends JFrame  {
    private c_Menu controller;

    JPanel panButtons;

    private JButton btnPvp;
    private JButton btnPve;
    private JButton btnAIvAI;
    private JButton btnCustomize;
    JButton btnInfo;
    private JCheckBox cbxTheme;


    /**
     * Constructor of the main menu. : Initiates the main frame.
     * @param controller Controller.
     */
    public MenuFrame(c_Menu controller){
        this.controller = controller;
        initComponents();
        initEvent();
        Util.initFrame(this, "Animal Arena - Main Menu", 500, 600);
    }

    // ************ INIT *****************

    /**
     * Initiates the frame's components.
     */
    private void initComponents(){
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // Label Welcome
        JLabel lblWelcome = new JLabel("<html>Welcome in Animals Arena, where you can experience RPG-like fights. <br>Please select the mode you'd like :</html>",
                SwingConstants.CENTER);
        lblWelcome.setBorder(new EmptyBorder(0,0,20,0));

        contentPanel.add(lblWelcome, BorderLayout.NORTH);

        // Choice buttons
        panButtons = new JPanel(new GridLayout(5,1, 10, 10));
        initButtons();
        contentPanel.add(panButtons, BorderLayout.CENTER);

        // Checkbox theme
        JPanel southPanel = new JPanel(new GridBagLayout());
        contentPanel.add(southPanel, BorderLayout.SOUTH);
        cbxTheme = new JCheckBox("Dark theme", true);
        var gc = Util.setGridBagConstraints(0, 0, 1, 1);
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        southPanel.add(cbxTheme, gc);
    }

    /**
     * Initiates the frame's buttons.
     */
    private void initButtons(){
        // Button PVP
        btnPvp = new JButton("<html>Player vs Player : Each player composes its animal for an intense fight !</html>");
        panButtons.add(btnPvp);

        // Button PVE
        btnPve = new JButton("<html>Player vs AI : Compose your animal and fight against a computer !</html>");
        panButtons.add(btnPve);

        // Button AIvAI
        btnAIvAI = new JButton("<html>AI vs AI : Spectate a fight between two AI !</html>");
        panButtons.add(btnAIvAI);

        // Button Customize
        btnCustomize = new JButton("<html>Create a fully customized animal.</html>");
        panButtons.add(btnCustomize);

        // Button info
        btnInfo = new JButton("<html> Consult animals and attacks' info");
        panButtons.add(btnInfo);
    }

    /**
     * Initiates the components' events
     */
    private void initEvent(){
        btnPvp.addActionListener(this::btnPvp_click);
        btnAIvAI.addActionListener(this::btnAIvAI_click);
        btnPve.addActionListener(this::btnPve_click);
        btnCustomize.addActionListener(this::btnCustomize_click);
        btnInfo.addActionListener(this::btnInfo_click);
        cbxTheme.addActionListener(this::cbxTheme_checkChange);

    }



    // ************* EVENTS ****************
    /**
     * When clicked, initiates two players and opens the creation menu.
     * @param e Event.
     */
    private void btnPvp_click(ActionEvent e) {
        Player p1 = new Player(Position.BOTTOM);
        Player p2 = new Player(Position.TOP);
        pickAnimalAndStartBattle(p1, p2);
    }

    /**
     * When clicked, initiates a player and an AI and opens the creation menu.
     * @param e Event.
     */
    private void btnPve_click(ActionEvent e) {
        Player p1 = new Player(Position.BOTTOM);
        PlayerAI p2 = new PlayerAI(Position.TOP);
        pickAnimalAndStartBattle(p1, p2);
    }

    /**
     * When clicked, initiates two AIs and opens the creation menu.
     * @param e Event.
     */
    private void btnAIvAI_click(ActionEvent e) {
        PlayerAI p1 = new PlayerAI(Position.BOTTOM);
        PlayerAI p2 = new PlayerAI(Position.TOP);
        pickAnimalAndStartBattle(p1, p2);
    }

    /**
     * When clicked, opens the customization menu.
     */
    private void btnCustomize_click(ActionEvent e) {
        new CustomizationMenu(controller, this);
    }

    private void btnInfo_click(ActionEvent actionEvent) {
        controller.openInfoFrame(this);
    }

    /**
     * When the checkbox is clicked, changes the current theme : Light or dark.
     * @param e Check change event.
     */
    private void cbxTheme_checkChange(ActionEvent e) {
        if(cbxTheme.isSelected()){
            controller.setTheme("Dark");
        }
        else{
            controller.setTheme("Light");
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    // ***************** METHODS *****************
    /**
     * Launches the battle if the two players and their animals are correctly initialized.
     * @param p1 First player
     * @param p2 Second player
     */
    private void pickAnimalAndStartBattle(Player p1, Player p2){
        controller.initiatePlayers(p1, p2);
        controller.openCreationMenuFrame();

        if(controller.areAnimalsInitiated()){
            controller.openBattleFrame();
        }
    }

}
