package View.Frames;

import Controllers.c_Battle;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.Animal;
import Model.playerAI.Concrete.Player;
import Model.Util.Position;
import View.BufferedText;
import View.ButtonColors;
import View.JButtonAttack;
import View.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BattleFrame extends JFrame {
    /**
     * Controler of the battle frame.
     */
    c_Battle controller;

    /**
     * Current player
     */
    Player currentPlayer;

    /**
     * Panel containing the components of the player playing on top the screen.
     */
    HashMap<animalComponents, JComponent> topPanel = new HashMap<>();
    /**
     * List of the attack buttons of the player playing on top of the screen, if it's not an AI.
     */
    ArrayList<JButtonAttack> topAttacks = new ArrayList<>();

    /**
     * Panel containing the components of the player playing on bottom the screen.
     */
    HashMap<animalComponents, JComponent> bottomPanel = new HashMap<>();
    /**
     * List of the attack buttons of the player playing on bottom of the screen, if it's not an AI.
     */
    ArrayList<JButtonAttack> bottomAttacks = new ArrayList<>();

    /**
     * Current turn, starting at 1 at the beginning of the battle.
     * <br>
     *     A turn is constituted of a move for each player, so two "subturns".
     */
    private int turn = 1;
    /**
     * Subturn number, 0 or 1, allowing to decide if it is the next turn or if it's the other player's move.
     */
    private int subturn = 0;

    /**
     * Label containing the current player and turn.
     */
    private JLabel lblHeader;
    /**
     * Label displaying all the information needed during a turn.
     */
    private JLabel lblFooter;

    /**
     * Used for iteration of the {@link #timer timer}.
     */
    int i = 0;

    /**
     * Text to display in the footer label.
     */
    String footerText;
    /**
     * Member used in the {@link #timer timer} member to display the text letter by letter.
     * Represents the text that has been displayed so far in the execution of the timer.
     */
    String textSoFar;
    /**
     * Timer displaying a text letter by letter, and then pauses after going to the next subturn.
     */
    Timer timer = new Timer(20, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){

            char[] character = footerText.toCharArray();
            int arrayNumber = character.length;

            String s = String.valueOf(character[i]);
            textSoFar += s;
            if(s.equals(".") && i < character.length -3){ // 2nd instruction is meant to avoid having a carriage return on the last period.
                textSoFar += "<br>";
            }
            lblFooter.setText(textSoFar + "</html>");
            i++;
            if(i == arrayNumber){
                i = 0;
              try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
              lblFooter.setText("");
                timer.stop();
                resume();
            }
        }
    });

    /**
     * Enumerator of the type of components displayed in a player's label. <br>
     *     Used to find a component easily in a hashmap.
     */
    public enum animalComponents{
        NAME,
        HP,
        STATUS,
        INFO,
        ATTACK;
    }

    /**
     * Constructor of the Battle Frame, opposing two players and their animal.
     * <br>
     *     Players and animals must be initialized in the controller beforehand.
     * @param controller Controller of the app
     * @see c_Battle
     */
    public BattleFrame(c_Battle controller){
        this.controller = controller;
        Util.initFrame(this, "Battle scene", 600, 600);
        setOnClose();
        setLayout(new BorderLayout(30, 30));
        initComponents();
        newTurn();

    }

    // *********** INIT ******************

    /**
     * Defines how the frame reacts when it is asked to close.
     */
    private void setOnClose() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.endGame();

            }
        };
        addWindowListener(exitListener);
    }

    /**
     * Initializes the JComponents of the frame.
     */
    private void initComponents() {
        // Panels and their layouts
        JPanel contentPanel = Util.setContentPane(this);

        JPanel pnlBottom = new JPanel();
        pnlBottom.setLayout(new GridBagLayout());

        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new GridBagLayout());

        lblHeader = new JLabel();
        updateHeader();
        var gc = Util.setGridBagConstraints(0, 0, 1, 1);
        gc.gridwidth = 3;
        pnlTop.add(lblHeader, gc);


        // ***** South Panel *****
        contentPanel.add(pnlBottom, BorderLayout.SOUTH);
            // Bottom Player info
        initPlayerPanel(pnlBottom, controller.getPlayer(Position.BOTTOM), 2, 0);

            // Attack Buttons
        initAttackButtons(pnlBottom, controller.getPlayer(Position.BOTTOM), 1, 0);

            // Empty label (for future .png)
        pnlBottom.add(new JLabel(""), Util.setGridBagConstraints(0, 0, 0.3, 1)); //empty label to split the grid

            // Event log Panel
        JPanel pnlLog = new JPanel();
        pnlLog.setLayout(new GridBagLayout());
        pnlLog.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        var gcLog = Util.setGridBagConstraints(0, 6, 0.3, 1);
        gcLog.gridwidth = 3;
        gcLog.ipady = 20;
        pnlBottom.add(pnlLog, gcLog);

                // Battle events label
        lblFooter = new JLabel();
        pnlLog.add(lblFooter, Util.setGridBagConstraints(0, 0, 0.95, 0.5));

               // Events log button
        JButton btnEventLog;
        btnEventLog = new JButton("i");
        btnEventLog.setSize(40, 50);
//        btnEventLog.setPreferredSize(new Dimension(3, 7));
        btnEventLog.addActionListener(e-> btnEventLog_click());
        gc = Util.setGridBagConstraints(1, 0, 0.05, 0.5);
        gc.anchor = GridBagConstraints.CENTER;
        pnlLog.add(btnEventLog, gc);



        // ***** North (foe - playerB/IA ) panel *****
        contentPanel.add(pnlTop, BorderLayout.NORTH);

        initPlayerPanel(pnlTop, controller.getPlayer(Position.TOP), 0, 1);

            // Attack buttons
        initAttackButtons(pnlTop, controller.getPlayer(Position.TOP), 2, 1);

            // Empty label : image of the animal goes here
        pnlTop.add(new JLabel(""), Util.setGridBagConstraints(3, 1, 0.5, 0.5));

    }

    /**
     * Initializes the components of a player's panel : Animal's infos, attacks...
     * @param panel Panel in which to initialize the components.
     * @param player Player to initialize.
     * @param columnInfo Column number of the panel in which to display the info (HP, statuses...)
     * @param startAtRow First row number. ex: '2' will display the name on row 2 and other info bellow, in the next rows.
     */
    private void initPlayerPanel(JPanel panel, Player player, int columnInfo, int startAtRow) {
        IAnimal animal = player.getAlly();
        HashMap<animalComponents, JComponent> components = getPanelComponents(player.getPosition());

        // Name
        JLabel lblName = new JLabel(animal.getName());
        panel.add(lblName, Util.setGridBagConstraints(columnInfo,startAtRow, 0.25, 1));
        components.put(animalComponents.NAME, lblName);

        //Status
        JLabel lblStatus = new JLabel();
        panel.add(lblStatus, Util.setGridBagConstraints(columnInfo, startAtRow+1, 0.25, 1));
        components.put(animalComponents.STATUS, lblStatus);

        // HP
        JLabel lblHP = new JLabel(controller.getHP(animal));
        panel.add(lblHP, Util.setGridBagConstraints(columnInfo, startAtRow+2, 0.25, 1));
        components.put(animalComponents.HP, lblHP);

        //Info
        JButton btnInfo = new JButton("Info");
        btnInfo.setSize(30, 15);
        var gcInfo = Util.setGridBagConstraints(columnInfo, startAtRow+3, 0.25, 1);
        gcInfo.fill = GridBagConstraints.NONE;
        gcInfo.anchor = GridBagConstraints.WEST;
        panel.add(btnInfo, gcInfo);
        components.put(animalComponents.INFO, btnInfo);
        btnInfo.addActionListener(e-> btnInfo_click(player));

    }

    /**
     * Initiates the attack buttons of a player.
     * @param panel Panel on which to display the buttons
     * @param player Player concerned
     * @param column Column on which to display the buttons.
     * @param row Row on which to display the buttons.
     */
    private void initAttackButtons(JPanel panel, Player player, int column, int row){

        IAnimal animal = player.getAlly();
        ArrayList<JButtonAttack> attackList = getAttackList(player.getPosition());

        JPanel attackPanel = new JPanel();
        attackPanel.setLayout(new GridLayout(animal.getAttacks().size(), 1));
        GridBagConstraints gc = Util.setGridBagConstraints(column, row, 0.35, 1);
//        gc.gridheight = getPanelComponents(player.getPosition()).size();
        gc.gridheight = 5;

        panel.add(attackPanel, gc);

        if(!player.isBot()){
            for (IAttack attack: animal.getAttacks()) {
                JButtonAttack btn = new JButtonAttack(attack);
                ButtonColors.setAttackButtonColor(btn, controller.getTheme());
                attackPanel.add(btn);
                attackList.add(btn);
                btn.addActionListener(e -> btnAttack_click(player, attack));
                btn.setToolTipText(attack.getDescription());
            }
        }
    }

    // ************** EVENTS ******************

    /**
     * Displays the log when the button is clicked.
     */
    private void btnEventLog_click() {
        new LogFrame(this);
    }

    /**
     * Displays the animal's info (stats) when the button is clicked.
     * @param player
     */
    private void btnInfo_click(Player player) {
        String infos = String.format("%s's stats :%n%s", player.getAlly(), player.getAlly().getStatDisplay());
        JOptionPane.showMessageDialog(null, infos);

    }

    /**
     * Performs the attack on the foe when the attack button is clicked.
     * @param player Player owning the attack
     * @param attack Attack concerned
     */
    private void btnAttack_click(Player player, IAttack attack) {
        Animal animal = player.getAlly();
        animal.attack(player.getFoe(), attack);
        updateAnimalsState();
        updateFooterDisplay();
    }

    // ***************** DISPLAY *******************

    /**
     * Refreshes the header with the current turn and player.
     */
    private void updateHeader() {
        lblHeader.setText(String.format("Turn n°%d - %s's move", turn, currentPlayer));
    }

    /**
     * Returns the components positionned on one side or the other
     * @param position Position of the components to get.
     * <br>
     *     Valid positions : {@link Model.Util.Position#BOTTOM} or {@link Model.Util.Position#BOTTOM}
     * @return a dictionnary, keys being the {@link BattleFrame.animalComponents} enum, values being the components.
     */
    private HashMap<animalComponents, JComponent> getPanelComponents(Position position) {
        return position.equals(Position.BOTTOM) ? bottomPanel : topPanel;
    }

    /**
     * Get the list of attack buttons of a position.
     * @param position position Position of the components to get. <br>
     * Valid positions : {@link Model.Util.Position#BOTTOM} or {@link Model.Util.Position#BOTTOM}
     * @return ArrayList of buttons for each attack.
     */
    private ArrayList<JButtonAttack> getAttackList(Position position) {
        return position.equals(Position.BOTTOM) ? bottomAttacks : topAttacks;
    }

    /**
     * Updates the footer label with new events. Displays it letter by letter.
     */
    private void updateFooterDisplay() {
        if(controller.hasTextToDisplay()){
            pause();
            footerText = controller.getTextToDisplay();
            textSoFar = "<html>";
            timer.start();
//            displayThread.run();
        }
        else turnHandler();
    }

    /**
     * Disables all the components that could be used by the user to "pause" the program.
     */
    private void pause(){
        enablePanel(Position.TOP, false);
        enablePanel(Position.BOTTOM, false);
    }

    /**
     * Resumes the program after a pause.
     */
    private void resume(){
        turnHandler();
    }

    /**
     * Handles the turns and hence, the components to enable.
     */
    private void turnHandler(){
        if(controller.isGameFinished()) {
            endGame(controller.getWinner());
            return;
        }
        subturn++;

        // New turn if both players have played
        if(subturn == 2){
            subturn = 0;
            turn++;
            controller.turnEnding();
            updateAnimalsState();
            newTurn();
        }

        // Second part of the turn if only one has played
        else {
            currentPlayer = controller.getOpponent(currentPlayer);
            playTurn();
        }
    }

    /**
     * Initiates a new turn, enables the components of the fastest animal.
     */
    private void newTurn(){
        BufferedText.addTurnToLog(turn);
        currentPlayer = controller.whichIsFaster();
        playTurn();
    }

    /**
     * Handles the turn of a player : Checks if the player can act, and handles the turn if the current player is an AI.
     */
    private void playTurn(){
        updateHeader();

        if(controller.canPlayerAct(currentPlayer)){
            enablePanel(currentPlayer);
            if(currentPlayer.isBot()){
                btnAttack_click(currentPlayer, controller.chooseAIMove(currentPlayer));
            }
        }
        else{
            turnHandler();
        }
    }

    /**
     * Displays a winning message.
     * @param winner Winner of the game.
     */
    private void endGame(Player winner) {
        enablePanel(Position.TOP, false);
        enablePanel(Position.BOTTOM, false);
        JOptionPane.showMessageDialog(null, String.format("%s wins !", winner.getAlly()));
        controller.endGame();

    }

    /**
     * Enables a player's panel, based on its position.
     * @param position {@link Model.Util.Position} enumerator value.
     * @param enable boolean indicating it it has to be enabled (true) or disabled (false).
     * @see #enablePanel(Player) enablePanel based on playr
     */
    private void enablePanel(Position position, boolean enable) {
        ArrayList<JButtonAttack> attackList = getAttackList(position);
        for(JButtonAttack btnAttack : attackList){
            btnAttack.setEnabled(enable);
        }
    }

    /**
     * Enable a player's panel.
     * @param player Player for whom to enable the panel.
     */
    private void enablePanel(Player player){
        enablePanel(player.getPosition(), true);
        enablePanel(player.getOppositePosition(), false);
    }

    /**
     * Updates the display of HP and statuses.
     */
    private void updateAnimalsState(){
        ((JLabel)topPanel.get(animalComponents.HP)).setText(controller.getHP(controller.getAnimal(Position.TOP)));
        ((JLabel)bottomPanel.get(animalComponents.HP)).setText(controller.getHP(controller.getAnimal(Position.BOTTOM)));

        ((JLabel)topPanel.get(animalComponents.STATUS)).setText(controller.getStatus(controller.getAnimal(Position.TOP)));
        ((JLabel)bottomPanel.get(animalComponents.STATUS)).setText(controller.getStatus(controller.getAnimal(Position.BOTTOM)));
    }
}

