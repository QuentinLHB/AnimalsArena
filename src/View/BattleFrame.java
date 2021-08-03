package View;

import Controler.c_Battle;
import Controler.c_Menu;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.playerAI.Concrete.PlayerAI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BattleFrame extends JFrame {
    c_Battle controler;
    PlayerAI CPUA;
    PlayerAI CPUB;

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
    
    public BattleFrame(c_Menu controler, PlayersEnum p1, PlayersEnum p2){
        this.controler = new c_Battle(controler);
        Util.initFrame(this, "Battle scene", 600, 600);
        setLayout(new BorderLayout(30, 30));
        setAI(p1, p2);
        initComponents();
        setVisible(true);
    }

    private void setAI(PlayersEnum p1, PlayersEnum p2){
        if(p1.isBot()){
            CPUA = new PlayerAI(controler.getAnimalA(), controler.getAnimalB());
        }
        if(p2.isBot()){
            CPUB = new PlayerAI(controler.getAnimalB(), controler.getAnimalA());
        }
    }

    private void initComponents() {
        //Window
        JPanel contentPanel = Util.setContentPane(this);

//       South (Ally - playerA) Panel
//        JPanel southPanel = new JPanel();
//        southPanel.setLayout(new GridBagLayout());


        JPanel grdAllysInfo = new JPanel();
        grdAllysInfo.setLayout(new GridBagLayout());
        contentPanel.add(grdAllysInfo, BorderLayout.SOUTH);
        createPlayerPanel(grdAllysInfo, controler.getAnimalA(), 1, 2);
        grdAllysInfo.add(new JLabel(""), setAllyGridConstraints(0, 0, 0.5, 0.5)); //empty label to split the grid
//        grdAllysInfo.add(grdAllysInfo, setAllyGridConstraints(1, 0, 0.5, 0.5));


        // North (foe - playerB/IA ) panel
        JPanel grdFoeInfo = new JPanel();
        grdFoeInfo.setLayout(new GridBagLayout());
        contentPanel.add(grdFoeInfo, BorderLayout.NORTH);
        createPlayerPanel(grdFoeInfo, controler.getAnimalB(), 1, 0);
        grdFoeInfo.add(new JLabel(""), setAllyGridConstraints(2, 0, 0.5, 0.5));
    }

    private void createPlayerPanel(JPanel panel, IAnimal animal, int columnAtk, int columnInfo) {
        HashMap<animalComponents, JComponent> components = new HashMap<>();
        // Name
        JLabel lblName = new JLabel(animal.getName());
        panel.add(lblName, setAllyGridConstraints(columnInfo,0, 0.5, 1));
        components.put(animalComponents.NAME, lblName);

        // HP
        JLabel lblHP = new JLabel(controler.getHP(animal));
        panel.add(lblHP, setAllyGridConstraints(columnInfo, 1, 0.5, 1));
        components.put(animalComponents.HP, lblHP);

        // Info button
        JButton btnInfo = new JButton("Info");
        panel.add(btnInfo, setAllyGridConstraints(columnInfo, 2, 0.5, 1));
        components.put(animalComponents.INFO, btnInfo);

        // Attacks
        JPanel attackPanel = new JPanel();
        attackPanel.setLayout(new GridLayout(animal.getAttacks().size(), 1));
        GridBagConstraints gc = setAllyGridConstraints(columnAtk, 0, 0.75, 1);
        gc.gridheight = 3;
        panel.add(attackPanel, gc);

        for (IAttack attack: animal.getAttacks()) {
            JButton btn = new JButton(attack.getAttackName());
            attackPanel.add(btn);
            btn.addActionListener(e -> controler.executeAttack(attack, animal));
        }
    }

    /**
     * Creates a GridBagConstraint with common property :
     * @param column position in the columns (1 : column 1).
     * @param row position in the rows (1 : row 1).
     * @return GridBagConstraint.
     */
    private GridBagConstraints setAllyGridConstraints(int column, int row, double weightClmn, double weightRow){
            GridBagConstraints gc = new GridBagConstraints();
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
    }

