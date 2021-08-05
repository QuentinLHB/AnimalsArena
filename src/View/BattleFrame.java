package View;

import Controler.c_Battle;
import Controler.c_Menu;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.Animal;
import Model.playerAI.Concrete.Player;
import Model.Util.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    String footerText;
    String textSoFar;
    int i = 0;
    Timer timer = new Timer(20, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){

            char character[] = footerText.toCharArray();
            int arrayNumber = character.length;

            String s = String.valueOf(character[i]);
            textSoFar += s;
            if(s.equals(".")){
                textSoFar += "<br>";
            }
            lblFooter.setText(textSoFar + "</html>");
            i++;
            if(i == arrayNumber){
                i = 0;
                timer.stop();
            }
        }
    });

    public enum animalComponents{
        NAME,
        HP,
        INFO,
        ATTACK;
    }
    
    public BattleFrame(c_Menu controler){
        this.controler = new c_Battle(controler);
        Util.initFrame(this, "Battle scene", 600, 600);
        setLayout(new BorderLayout(30, 30));
        initComponents();
        turn();
        setVisible(true);

    }

    private void initComponents() {
        // Panels and their layouts
        JPanel contentPanel = Util.setContentPane(this);

        JPanel grdBottomInfo = new JPanel();
        grdBottomInfo.setLayout(new GridBagLayout());

        JPanel grdTopInfo = new JPanel();
        grdTopInfo.setLayout(new GridBagLayout());

        lblHeader = new JLabel();
        updateHeader();
        var gc = setAllyGridConstraints(0, 0, 1, 1);
        gc.gridwidth = 3;
        grdTopInfo.add(lblHeader, gc);



        contentPanel.add(grdBottomInfo, BorderLayout.SOUTH);
        createPlayerPanel(grdBottomInfo, controler.getPlayer(Position.BOTTOM), 1, 2, 0);
        grdBottomInfo.add(new JLabel(""), setAllyGridConstraints(0, 0, 0.5, 0.5)); //empty label to split the grid
        lblFooter = new JLabel();
        gc = setAllyGridConstraints(0, 10, 1, 1);
        gc.gridwidth = 3;
        grdBottomInfo.add(lblFooter, gc);

        // North (foe - playerB/IA ) panel
        contentPanel.add(grdTopInfo, BorderLayout.NORTH);
        createPlayerPanel(grdTopInfo, controler.getPlayer(Position.TOP), 1, 0, 1);
        grdTopInfo.add(new JLabel(""), setAllyGridConstraints(2, 1, 0.5, 0.5));
    }

    private void updateHeader() {
        lblHeader.setText(String.format("Turn n°%d - %s's move", turn, currentPlayer));
    }

    private void createPlayerPanel(JPanel panel, Player player, int columnAtk, int columnInfo, int startAtRow) {
        IAnimal animal = player.getAlly();

        HashMap<animalComponents, JComponent> components = getPanelComponents(player.getPosition());
        ArrayList<JButtonAttack> attackList = getAttackList(player.getPosition());

        // Name
        JLabel lblName = new JLabel(animal.getName());
        panel.add(lblName, setAllyGridConstraints(columnInfo,startAtRow, 0.5, 1));
        components.put(animalComponents.NAME, lblName);

        // HP
        JLabel lblHP = new JLabel(controler.getHP(animal));
        panel.add(lblHP, setAllyGridConstraints(columnInfo, startAtRow+1, 0.5, 1));
        components.put(animalComponents.HP, lblHP);


        // Info button
        JButton btnInfo = new JButton("Info");
        panel.add(btnInfo, setAllyGridConstraints(columnInfo, startAtRow+2, 0.5, 1));
        components.put(animalComponents.INFO, btnInfo);
        btnInfo.addActionListener(e-> btnInfo_click(player));

        // Attacks
        JPanel attackPanel = new JPanel();
        attackPanel.setLayout(new GridLayout(animal.getAttacks().size(), 1));
        GridBagConstraints gc = setAllyGridConstraints(columnAtk, startAtRow, 0.75, 1);
        gc.gridheight = 3;
        panel.add(attackPanel, gc);

        for (IAttack attack: animal.getAttacks()) {
            JButtonAttack btn = new JButtonAttack(attack);
            attackPanel.add(btn);
            attackList.add(btn);
            btn.addActionListener(e -> btnAttack_click(player, attack));
            btn.setToolTipText(attack.getDescription());
        }
    }

    /**
     * Creates a GridBagConstraint with common property :
     * @param column position in the columns (1 : column 1).
     * @param row position in the rows (1 : row 1).
     * @return GridBagConstraint.
     */
    private GridBagConstraints setAllyGridConstraints(int column, int row, double weightClmn, double weightRow){
        var gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.ipady = 5;
        gc.ipadx = 30;
        gc.weightx = weightClmn;
        gc.weighty = weightRow;

        gc.anchor = GridBagConstraints.NORTH;
        gc.gridx = column;
        gc.gridy = row;

        return gc;
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
        updateHP();
        updateFooterDisplay();
        if(controler.isGameFinished()) endGame(controler.getWinner());
        else subTurn(controler.getOpponent(player));
    }

    /**
     * Updates the footer label with new events. Displays it letter by letter.
     */
    private void updateFooterDisplay() {
        if(controler.hasTextToDisplay()){
            footerText = controler.getTextToDisplay();
            textSoFar = "<html>";
            timer.start();
        }

    }

    private void turn(){
        currentPlayer = controler.whichIsFaster();
        updateHeader();

        if(controler.canPlayerAct(currentPlayer)){
            enablePanel(currentPlayer);
        }
        else{
            updateFooterDisplay();
            subTurn(controler.getOpponent(currentPlayer));
        }
    }

    /**
     * Initiation phase of a player's turn to play : Update the turn, enable the appropriate components.
     * @param player Player to initiate.
     */
    private void subTurn(Player player){
        subturn++;
        // New turn if both players have played
        if(subturn == 2){
            subturn = 0;
            turn++;
            controler.turnEnding();
            updateFooterDisplay();
            updateHP();
            turn();
        }

        // Second part of the turn if only one has played
        else {
            currentPlayer = player;
            updateHeader();
            enablePanel(currentPlayer);
        }
    }

    private void endGame(Player winner) {
        enablePanel(Position.TOP, false);
        enablePanel(Position.BOTTOM, false);
        JOptionPane.showMessageDialog(null, String.format(""));

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

    private void updateHP(){
        ((JLabel)topPanel.get(animalComponents.HP)).setText(controler.getHP(controler.getAnimal(Position.TOP)));
        ((JLabel)bottomPanel.get(animalComponents.HP)).setText(controler.getHP(controler.getAnimal(Position.BOTTOM)));
    }
}

