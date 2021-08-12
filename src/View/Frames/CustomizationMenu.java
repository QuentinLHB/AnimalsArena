package View.Frames;

import Controllers.c_Menu;
import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.AttackEnum;
import Model.Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Model.Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Model.Animal.Creation.Concrete.Animal;
import Model.Animal.Creation.Concrete.StatID;
import Model.Util.RNG;
import Model.playerAI.Concrete.Player;
import View.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;

public class CustomizationMenu extends JDialog {
    private c_Menu controller;
    private Player currentPlayer;
    private Animal animalToLoad;

    private JLabel lblTitle;
    public JTextField lblNickname;
    public JCheckBox cbxBalanced;
    public EnumMap<StatID, JSlider> sliders;
    public JList lstChosenAttacks;
    public DefaultListModel<AttackEnum> listModel = new DefaultListModel<>();
    public JComboBox<AttackEnum> cboAttacks;
    public JComboBox<AttackBehaviorEnum> cboAtkBhv;
    public JComboBox<DieBehaviorEnum> cboDieBhv;
    public JComboBox<DefendBehaviorEnum> cboDefBhv;


    final int MIN_SLIDERVALUE = 50;
    final int MAX_SLIDERVALUE = 150;
    final int DEFAULT_SLIDERVALUE = 100;


    /**
     * Constructor of the customization frame, that allows the user to create an animal.
     * @param controller {@link Controllers.c_Menu Menu Controller}
     * @param owner Frame that needs to stay open while this frame is active.
     * @param currentPlayer Player creating its custom animal
     */
    public CustomizationMenu(c_Menu controller, JDialog owner, Player currentPlayer){
        super(owner, true);
        init(controller, currentPlayer);
    }

    public CustomizationMenu(c_Menu controller, JFrame owner){
        super(owner, true);
        init(controller, null);
    }

    public CustomizationMenu(c_Menu controller, JDialog owner, Animal animal, Player currentPlayer){
        super(owner, true);
        animalToLoad = animal;
        this.currentPlayer = currentPlayer;
        init(controller, currentPlayer);
    }

    private void init(c_Menu controler, Player currentPlayer){
        this.controller = controler;
        this.currentPlayer = currentPlayer;
        initComponents();
        controler.setCustomizationFrame(this);
        loadAnimal();
        Util.initFrame(this, "Customization", 650, 700);
    }

    private void loadAnimal() {
        if(animalToLoad == null) return;

        lblNickname.setText(animalToLoad.getName());

        cbxBalanced.setSelected(false);
        for(Map.Entry<StatID, JSlider> statSlider : sliders.entrySet()){
            JSlider slider = statSlider.getValue();
            StatID statID = statSlider.getKey();
            int stat = statID.equals(StatID.MAX_HEALTH) ?
                    Math.round(animalToLoad.getStat(statID)) : // Max health is on base 100
                    Math.round(animalToLoad.getStat(statID)*100); // Other stats on base 1
            slider.setValue(stat);
        }

        listModel.clear(); // Removes pre-loaded Defend
        for(IAttack attack : animalToLoad.getAttacks()){
            listModel.addElement(attack.getAttackEnum());
        }

        cboAtkBhv.setSelectedItem(animalToLoad.getAttackBehavior().getAttackBhvEnum());
        cboDefBhv.setSelectedItem(animalToLoad.getDefendBehavior().getDefendBhvEnum());
        cboDieBhv.setSelectedItem(animalToLoad.getDieBehavior().getDieBhvEnum());

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
        JScrollPane scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

            // Nickname
        final String TEXT = "Name";
        lblNickname = new JTextField(TEXT);
        lblNickname.setForeground(Color.GRAY);
        lblNickname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lblNickname.getText().equals(TEXT)) {
                    lblNickname.setText("");
                    lblNickname.setForeground(UIManager.getDefaults().getColor("FormattedTextField.foreground"));
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
        var gc = Util.setGridBagConstraints(0,0,1, 0.3);
        pnlCenter.add(lblNickname, gc);

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
        int row = 0;

        JPanel pnlStatOptions = new JPanel(new FlowLayout());
        var gc = Util.setGridBagConstraints(0, row, 1, 1);
        gc.gridwidth = 10;
        panel.add(pnlStatOptions, gc);

        cbxBalanced = new JCheckBox("Balanced", true);
        pnlStatOptions.add(cbxBalanced, Util.setGridBagConstraints(0, row, 0.1, 1));

        JButton btnReset = new JButton("Reset");
        pnlStatOptions.add(btnReset, Util.setGridBagConstraints(1, row, 0.2, 1));
        btnReset.addActionListener(this::btnResetStats_click);

        JButton btnRandomStats = new JButton("Randomize");
        pnlStatOptions.add(btnRandomStats, Util.setGridBagConstraints(2, row, 0.2, 1));
        btnRandomStats.addActionListener(this::btnRandomStats_click);

        row++;

        for(StatID statID: StatID.values()){
            if(statID.equals(StatID.ACCURACY)) continue;
            // Stat display
            JLabel lblStat = new JLabel(String.format("%s :", statID.name().toLowerCase(Locale.ROOT)));
            gc = Util.setGridBagConstraints(0, row, 0.1, 1);
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

    private void btnRandomStats_click(ActionEvent e) {
        for (JSlider slider :
                sliders.values()) {
            slider.setValue(RNG.GenerateNumber(MIN_SLIDERVALUE, MAX_SLIDERVALUE));
        }
    }

    private void btnResetStats_click(ActionEvent e) {
        /* Changing the sliders' values triggers the event :
        * if the box is checked, values are not reseted to 100.
        * Hence the uncheck of the box during the method. Not ideal, but hey, don't judge me.
         */
        boolean isCbxChecked = cbxBalanced.isSelected();
        if(isCbxChecked) cbxBalanced.setSelected(false); //
        for (JSlider slider : sliders.values()) { slider.setValue(100);}
        if(isCbxChecked) cbxBalanced.setSelected(true);
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
        // Not balanced
        if(!cbxBalanced.isSelected()){
            lblValue.setText(String.valueOf(statSlider.getValue()));
            return;
        }

        cbxBalanced.setSelected(false); // Avoid the snowball effect of the triggered events
        // Balanced
        int value = statSlider.getValue();
        int valueToAdd = 0;

        if(value % (StatID.values().length-1) == 0){
            if(value > Integer.parseInt(lblValue.getText())) valueToAdd = -1; // User raised the slider : Other stats are lowered
            else if(value < Integer.parseInt(lblValue.getText())) valueToAdd = 1;
            for (JSlider slider : sliders.values()) {
                if(slider.equals(statSlider)) continue;
                slider.setValue(slider.getValue()+valueToAdd);
            }
        }


        lblValue.setText(String.valueOf(statSlider.getValue()));
        cbxBalanced.setSelected(true);
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
        Animal animal = controller.validateCustomAnimal(currentPlayer, animalToLoad);
        Util.printCreationConfirmation(animal);
        Util.exit(this);
    }
}
