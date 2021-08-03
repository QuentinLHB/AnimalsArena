package View;

import Controler.c_Battle;
import Controler.c_Menu;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.playerAI.Concrete.Player;
import Model.Util.Position;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BattleFrame extends JFrame {
    c_Battle controler;

//    Player p1;
//    Player p2;
    Player currentPlayer;
//    PlayerAI CPUA;
//    PlayerAI CPUB;

    HashMap<animalComponents, JComponent> topPanel = new HashMap<>();
    HashMap<animalComponents, JComponent> bottomPanel = new HashMap<>();


    private int turn = 1;

    IAnimal currentAnimalPlaying;

    JLabel lblHeader;

//    //Ally
//    JLabel lblAllyName;
//    JLabel lblAllyHP;
//    JButton btnAllyInfo;
//    ArrayList<JButton> allysAttacks;
//
//    //Foe
//    JLabel lblFoeName;
//    JLabel lblFoeHP;
//    JButton btnFoeInfo;
//    ArrayList<JButton> FoesAttacks;

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
        setVisible(true);
        startBattle();
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
//        grdBottomInfo.add(grdBottomInfo, setAllyGridConstraints(1, 0, 0.5, 0.5));


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

        HashMap<animalComponents, JComponent> components = player.getPosition().equals(Position.BOTTOM) ? bottomPanel : topPanel;
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

        // Attacks
        JPanel attackPanel = new JPanel();
        attackPanel.setLayout(new GridLayout(animal.getAttacks().size(), 1));
        GridBagConstraints gc = setAllyGridConstraints(columnAtk, startAtRow, 0.75, 1);
        gc.gridheight = 3;
        panel.add(attackPanel, gc);

        for (IAttack attack: animal.getAttacks()) {
            JButton btn = new JButton(attack.getAttackName());
            attackPanel.add(btn);
            btn.addActionListener(e -> executeAttack(attack));
        }
    }

    private void executeAttack(IAttack attack) {

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

    private void startBattle() {
        currentPlayer = controler.whichIsFaster();
        if(currentPlayer.getPosition().equals(Position.TOP)){
            enablePanel(Position.TOP, true);
            enablePanel(Position.BOTTOM, false);
        }
    }

    private void enablePanel(Position position, boolean enable) {
        if(position.equals(Position.TOP)){
            for(JComponent component : topPanel.values()){
                component.setEnabled(enable);
            }
        }

    }
//
//    /**
//     * Get the player associated to an animal.
//     * @param animal Animal
//     * @return Player Enum possessing the animal.
//     */
//    private PlayersEnum getPlayer(IAnimal animal){
//        for (Map.Entry<PlayersEnum, IAnimal> entry : players.entrySet()){
//            if(entry.getValue().equals(animal)) return entry.getKey();
//        }
//        return null;
//    }

}

