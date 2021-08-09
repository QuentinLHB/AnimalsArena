package View.Frames;

import Controler.c_Battle;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.Animal;
import Model.playerAI.Concrete.Player;
import Model.Util.Position;
import View.BufferedText;
import View.JButtonAttack;
import View.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BattleFrame extends JFrame {
    c_Battle controler;

//    Player p1;
//    Player p2;
    Player currentPlayer;
//    PlayerAI CPUA;
//    PlayerAI CPUB;

    HashMap<animalComponents, JComponent> topPanel = new HashMap<>();
    ArrayList<JButtonAttack> topAttacks = new ArrayList<>();

    HashMap<animalComponents, JComponent> bottomPanel = new HashMap<>();
    ArrayList<JButtonAttack> bottomAttacks = new ArrayList<>();

    private int turn = 1;
    private int subturn = 0;

//    IAnimal currentAnimalPlaying;

    JLabel lblHeader;
    JLabel lblFooter;

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

//    DisplayThread displayThread = new DisplayThread(timer);

    String footerText;
    String textSoFar;
    int i = 0;


    public enum animalComponents{
        NAME,
        HP,
        STATUS,
        INFO,
        ATTACK;
    }
    
    public BattleFrame(c_Battle controler){
        this.controler = controler;
        Util.initFrame(this, "Battle scene", 600, 600);
        setOnClose();
        setLayout(new BorderLayout(30, 30));
        initComponents();
        newTurn();

    }

    private void setOnClose() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controler.endGame();

            }
        };
        addWindowListener(exitListener);
    }

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
        createPlayerPanel(pnlBottom, controler.getPlayer(Position.BOTTOM), 2, 0);

            // Attack Buttons
        setAttackButtons(pnlBottom, controler.getPlayer(Position.BOTTOM), 1, 0);

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

        createPlayerPanel(pnlTop, controler.getPlayer(Position.TOP), 0, 1);

            // Attack buttons
        setAttackButtons(pnlTop, controler.getPlayer(Position.TOP), 2, 1);

            // Empty label : image of the animal goes here
        pnlTop.add(new JLabel(""), Util.setGridBagConstraints(3, 1, 0.5, 0.5));

    }

    private void btnEventLog_click() {
        new LogFrame();
    }

    private void updateHeader() {
        lblHeader.setText(String.format("Turn n°%d - %s's move", turn, currentPlayer));
    }

    private void createPlayerPanel(JPanel panel, Player player, int columnInfo, int startAtRow) {
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
        JLabel lblHP = new JLabel(controler.getHP(animal));
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

    private void setAttackButtons(JPanel panel, Player player, int column, int row){

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
                attackPanel.add(btn);
                attackList.add(btn);
                btn.addActionListener(e -> btnAttack_click(player, attack));
                btn.setToolTipText(attack.getDescription());
            }
        }
    }



    private HashMap<animalComponents, JComponent> getPanelComponents(Position position) {
        return position.equals(Position.BOTTOM) ? bottomPanel : topPanel;
    }

    private ArrayList<JButtonAttack> getAttackList(Position position) {
        return position.equals(Position.BOTTOM) ? bottomAttacks : topAttacks;
    }

    private void btnInfo_click(Player player) {
        String infos = String.format(player.getAlly().getStatDisplay());
        JOptionPane.showMessageDialog(null, infos);

    }

    private void btnAttack_click(Player player, IAttack attack) {
        Animal animal = player.getAlly();
        animal.attack(player.getFoe(), attack);
        updateAnimalsState();
        updateFooterDisplay();
//        turnHandler(controler.getOpponent(player));
//        if(controler.isGameFinished()) endGame(controler.getWinner());
//        else ;
    }

    /**
     * Updates the footer label with new events. Displays it letter by letter.
     */
    private void updateFooterDisplay() {
        if(controler.hasTextToDisplay()){
            pause();
            footerText = controler.getTextToDisplay();
            textSoFar = "<html>";
            timer.start();
//            displayThread.run();
        }
        else turnHandler(controler.getOpponent(currentPlayer));
    }

    private void pause(){
        enablePanel(Position.TOP, false);
        enablePanel(Position.BOTTOM, false);
    }

    private void resume(){
        turnHandler(controler.getOpponent(currentPlayer));
    }

    /**
     * Initiation phase of a player's turn to play : Update the turn, enable the appropriate components.
     * @param player Player to initiate.
     */
    private void turnHandler(Player player){
        if(controler.isGameFinished()) {
            endGame(controler.getWinner());
            return;
        }
        subturn++;

        // New turn if both players have played
        if(subturn == 2){
            subturn = 0;
            turn++;
            controler.turnEnding();
//            updateFooterDisplay();
            updateAnimalsState();
            newTurn();
        }

        // Second part of the turn if only one has played
        else {
            currentPlayer = player;
            playTurn();
        }
    }

    private void newTurn(){
        BufferedText.addTurnToLog(turn);
        currentPlayer = controler.whichIsFaster();
        playTurn();
    }

    private void playTurn(){
        updateHeader();

        if(controler.canPlayerAct(currentPlayer)){
            enablePanel(currentPlayer);
            if(currentPlayer.isBot()){
                btnAttack_click(currentPlayer, controler.chooseAIMove(currentPlayer));
            }
        }
        else{
//            updateFooterDisplay();
            turnHandler(controler.getOpponent(currentPlayer));
        }
    }

    private void endGame(Player winner) {
        enablePanel(Position.TOP, false);
        enablePanel(Position.BOTTOM, false);
        JOptionPane.showMessageDialog(null, String.format("%s wins !", winner.getAlly()));
        controler.endGame();

    }

    private void enablePanel(Position position, boolean enable) {
        ArrayList<JButtonAttack> attackList = getAttackList(position);
        for(JButtonAttack btnAttack : attackList){
            btnAttack.setEnabled(enable);
        }
    }

    private void enablePanel(Player player){
        enablePanel(player.getPosition(), true);
        enablePanel(player.getOppositePosition(), false);
    }

    /**
     * Updates the display of HP and statuses.
     */
    private void updateAnimalsState(){
        ((JLabel)topPanel.get(animalComponents.HP)).setText(controler.getHP(controler.getAnimal(Position.TOP)));
        ((JLabel)bottomPanel.get(animalComponents.HP)).setText(controler.getHP(controler.getAnimal(Position.BOTTOM)));

        ((JLabel)topPanel.get(animalComponents.STATUS)).setText(controler.getStatus(controler.getAnimal(Position.TOP)));
        ((JLabel)bottomPanel.get(animalComponents.STATUS)).setText(controler.getStatus(controler.getAnimal(Position.BOTTOM)));
    }
}

