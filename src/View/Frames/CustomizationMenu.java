package View.Frames;

import Controllers.c_Menu;
import Model.Action.Attack.Concrete.AttackEnum;
import Model.Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Model.Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Model.Animal.Creation.Concrete.Animal;
import Model.Animal.Creation.Concrete.StatID;
import Model.playerAI.Concrete.Player;
import View.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;

public class CustomizationMenu extends JDialog {
    private c_Menu controler;
    private Player currentPlayer;

    private JLabel lblTitle;
    public JTextField lblNickname;
    public EnumMap<StatID, JSlider> sliders;
    public JList lstChosenAttacks;
    public DefaultListModel<AttackEnum> listModel = new DefaultListModel<>();
    public JComboBox<AttackEnum> cboAttacks;
    public JComboBox<AttackBehaviorEnum> cboAtkBhv;
    public JComboBox<DieBehaviorEnum> cboDieBhv;
    public JComboBox<DefendBehaviorEnum> cboDefBhv;


    /**
     * Constructor of the customization frame, that allows the user to create an animal.
     * @param controler {@link Controllers.c_Menu Menu Controller}
     * @param owner Frame that needs to stay open while this frame is active.
     * @param currentPlayer Player creating its custom animal
     */
    public CustomizationMenu(c_Menu controler, JDialog owner, Player currentPlayer){
        super(owner, true);
        this.controler = controler;
        this.currentPlayer = currentPlayer;
        initComponents();
        controler.setCustomizationFrame(this);
        Util.initFrame(this, "Customization", 500, 700);

    }

    /**
     * Initializes the frame's components.
     */
    private void initComponents(){
        JPanel contentPanel = Util.setContentPane(this);

        lblTitle = new JLabel("Create your own custom animal !");
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        // Center panel : owns all the other Panels
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new GridBagLayout());
        contentPanel.add(pnlCenter);

            // Nickname
        final String TEXT = "Name";
        lblNickname = new JTextField(TEXT);
        lblNickname.setForeground(Color.GRAY);
        lblNickname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lblNickname.getText().equals(TEXT)) {
                    lblNickname.setText("");
                    lblNickname.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (lblNickname.getText().isEmpty()) {
                    lblNickname.setForeground(Color.GRAY);
                    lblNickname.setText(TEXT);
                }
            }
        });
        pnlCenter.add(lblNickname, Util.setGridBagConstraints(0,0,1, 0.3));

            // Stats : un panel
        JPanel pnlStat = new JPanel();
        pnlStat.setLayout(new GridBagLayout());
        pnlCenter.add(pnlStat, Util.setGridBagConstraints(0, 1, 1, 0.3));
        initStatSliders(pnlStat);

            // Attacks : Un panel
        JPanel pnlAttacks = new JPanel();
        pnlAttacks.setLayout(new GridBagLayout());
        pnlCenter.add(pnlAttacks, Util.setGridBagConstraints(0, 2, 1, 0.3));
        initAttackSelectionComponents(pnlAttacks);

            // Behaviors : un Panel
        JPanel pnlBehaviors = new JPanel();
        pnlBehaviors.setLayout(new GridBagLayout());
        pnlCenter.add(pnlBehaviors, Util.setGridBagConstraints(0, 3, 1, 0.3));
        initBehaviorSelectionComponents(pnlBehaviors);

            // Creation button
        JButton btnCreateCustomAnimal = new JButton("OK");
        btnCreateCustomAnimal.addActionListener(this::btnCreateCustomAnimal_click);
        pnlCenter.add(btnCreateCustomAnimal, Util.setGridBagConstraints(0, 4, 1, 0.3));
    }

    /**
     * Initializes the sliders used to create stats
     * @param panel Panel in which to display the components
     */
    private void initStatSliders(JPanel panel) {
        sliders = new EnumMap<>(StatID.class);

        final int MIN_SLIDERVALUE = 50;
        final int MAX_SLIDERVALUE = 150;
        final int DEFAULT_SLIDERVALUE = 100;

        int row = 0;
        for(StatID statID: StatID.values()){
            if(statID.equals(StatID.ACCURACY)) continue;
            // Stat display
            JLabel lblStat = new JLabel(String.format("%s :", statID.name().toLowerCase(Locale.ROOT)));
            var gc = Util.setGridBagConstraints(0, row, 0.1, 1);
            gc.gridheight = 2;
            panel.add(lblStat, gc);

            // Slider value
            JLabel lblValue = new JLabel(String.valueOf(DEFAULT_SLIDERVALUE));
            gc = Util.setGridBagConstraints(1, row, 0.1, 1);
            panel.add(lblValue, gc);

            // Slider
            JSlider statSlider = new JSlider(MIN_SLIDERVALUE, MAX_SLIDERVALUE, DEFAULT_SLIDERVALUE);
            panel.add(statSlider, Util.setGridBagConstraints(2, row++, 0.8, 1));
            statSlider.setPaintTicks(true);
            statSlider.setMajorTickSpacing(20);
            statSlider.setMinorTickSpacing(5);
            statSlider.addChangeListener(e-> statSlider_valueChange(statSlider, lblValue));
            sliders.put(statID, statSlider);
        }
    }

    /**
     * Initializes the components ued to create the attacks.
     * @param pnlAttacks Panel in which to display the components
     */
    private void initAttackSelectionComponents(JPanel pnlAttacks) {
        cboAttacks = new JComboBox<>(AttackEnum.values());
        cboAttacks.removeItem(AttackEnum.DEFEND);
        pnlAttacks.add(cboAttacks, Util.setGridBagConstraints(0, 0, 0.7, 1));

        // List containing the chosen attacks
        listModel.addElement(AttackEnum.DEFEND);
        lstChosenAttacks = new JList(listModel);
        lstChosenAttacks.setSize(20, 100);
        lstChosenAttacks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstChosenAttacks.setVisibleRowCount(5);
        lstChosenAttacks.setVisible(true);
        var gc = Util.setGridBagConstraints(0, 1, 1, 1);
        pnlAttacks.add(lstChosenAttacks, gc);

        // Validation Button
        JButton btnAddAttack = new JButton("Add");
        btnAddAttack.addActionListener(this::btnAddAtk_click);
        pnlAttacks.add(btnAddAttack, Util.setGridBagConstraints(1, 0, 0.3, 1));

        // Delete Button
        JButton btnRemove = new JButton("X");
        gc = Util.setGridBagConstraints(1, 1,  0.1, 1);
        btnRemove.addActionListener(this::btnRemoveAtk_click);
        pnlAttacks.add(btnRemove, gc);

    }

    /**
     * Initializes the components used to determine the animal's behavior
     * @param pnlBehaviors Panel in which to display the components.
     */
    private void initBehaviorSelectionComponents(JPanel pnlBehaviors) {
        int i = 0;
        JLabel lblAttackBehavior = new JLabel("Attack behavior :");
        pnlBehaviors.add(lblAttackBehavior, Util.setGridBagConstraints(0,i++, 1, 1));
//        for(AttackBehaviorEnum)

        cboAtkBhv = new JComboBox<>(AttackBehaviorEnum.values());
        pnlBehaviors.add(cboAtkBhv, Util.setGridBagConstraints(0, i++, 1, 1));

        JLabel lblDefendBehavior = new JLabel("Defend Behavior :");
        pnlBehaviors.add(lblDefendBehavior, Util.setGridBagConstraints(0, i++, 1, 1));

        cboDefBhv = new JComboBox<>(DefendBehaviorEnum.values());
        pnlBehaviors.add(cboDefBhv, Util.setGridBagConstraints(0, i++, 1, 1));

        JLabel lblDieBehavior = new JLabel("Death Behavior :");
        pnlBehaviors.add(lblDieBehavior, Util.setGridBagConstraints(0, i++, 1, 1));

        cboDieBhv = new JComboBox<>(DieBehaviorEnum.values());
        pnlBehaviors.add(cboDieBhv, Util.setGridBagConstraints(0, i, 1, 1));
    }

    /**
     * Displays the new value of the slider in a label when it is slided.
     * @param statSlider slider that was slided
     * @param lblValue label associated with the slider.
     */
    private void statSlider_valueChange(JSlider statSlider, JLabel lblValue) {
        lblValue.setText(String.valueOf(statSlider.getValue()));
    }


    /**
     * When clicked, adds the attack selected in the {@link #cboAttacks attack combobox}.
     * @param e Event
     */
    private void btnAddAtk_click(ActionEvent e) {
        if(listModel.getSize() < 5){
            AttackEnum attack = (AttackEnum)cboAttacks.getSelectedItem();
            listModel.addElement(attack);
            cboAttacks.removeItem(cboAttacks.getSelectedItem());
            cboAttacks.setSelectedIndex(0);
            cboAttacks.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null, "The animal already reached its maximal capacity.");
        }
    }

    /**
     * When clicked, removes the selected attack from the {@link #lstChosenAttacks choen attack list}.
     * @param e Event
     */
    private void btnRemoveAtk_click(ActionEvent e) {
        if(lstChosenAttacks.getSelectedIndex() != -1){
            if(lstChosenAttacks.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Defend can't be deleted.");
                return;
            }
            AttackEnum selectedItem = listModel.get(lstChosenAttacks.getSelectedIndex());
            listModel.remove(lstChosenAttacks.getSelectedIndex());
            cboAttacks.insertItemAt(selectedItem, 0);
            if(!listModel.isEmpty()){
                lstChosenAttacks.setSelectedIndex(0);
                cboAttacks.setSelectedIndex(0);
                cboAttacks.requestFocus();
            }
        }
    }

    /**
     * Creates the animal
     * @param e Event
     */
    private void btnCreateCustomAnimal_click(ActionEvent e) {
        Animal animal = controler.createCustomAnimal(currentPlayer);
        Util.printCreationConfirmation(animal);
        Util.exit(this);
    }
}
