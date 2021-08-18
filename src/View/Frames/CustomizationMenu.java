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
import View.ButtonColors;
import View.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CustomizationMenu extends JDialog {
    private c_Menu controller;
    private Player currentPlayer;
    private Animal animalToLoad;

    private JLabel lblTitle;
    public JTextField txtName;
    public JCheckBox cbxBalanced;
    public EnumMap<StatID, JSlider> sliders;
    public JList lstChosenAttacks;
    public DefaultListModel<AttackEnum> listModel = new DefaultListModel<>();
    public JComboBox<AttackEnum> cboAttacks;
    public JComboBox<AttackBehaviorEnum> cboAtkBhv;
    public JComboBox<DieBehaviorEnum> cboDieBhv;
    public JComboBox<DefendBehaviorEnum> cboDefBhv;

    final String DEFAULT_NICKNAME = "Name";
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

    /**
     * Creates the frame in a context where there is no animal to initialize and no player to add the animal to.
     * @param controller Controller
     * @param owner parent frame.
     */
    public CustomizationMenu(c_Menu controller, JFrame owner){
        super(owner, true);
        init(controller, null);
    }

    /**
     * Creates the frame in a context where there is an animal to display and a player to add the animal to.
     * @param controller Controller
     * @param owner Parent frame
     * @param animal Animal to display in the frame
     * @param currentPlayer Player whom the animal must be added to.
     */
    public CustomizationMenu(c_Menu controller, JDialog owner, Animal animal, Player currentPlayer){
        super(owner, true);
        animalToLoad = animal;
        this.currentPlayer = currentPlayer;
        init(controller, currentPlayer);
    }

    /**
     * Initializes the frame.
     * @param controller Controller
     * @param currentPlayer Current player
     */
    private void init(c_Menu controller, Player currentPlayer){
        this.controller = controller;
        this.currentPlayer = currentPlayer;
        initComponents();
        controller.setCustomizationFrame(this);
        loadAnimal();
        Util.initFrame(this, "Customization", 650, 700);
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

            // Nickname

        txtName = new JTextField(DEFAULT_NICKNAME);
        txtName.setForeground(Color.GRAY);
        txtName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtName.getText().equals(DEFAULT_NICKNAME)) {
                    txtName.setText("");
                    txtName.setForeground(UIManager.getDefaults().getColor("FormattedTextField.foreground"));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtName.getText().isEmpty()) {
                    txtName.setForeground(Color.GRAY);
                    txtName.setText(DEFAULT_NICKNAME);
                }
            }
        });
        var gc = Util.setGridBagConstraints(0,0,1, 0.3);
        pnlCenter.add(txtName, gc);

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
        ButtonColors.setValidationBtnColor(btnCreateCustomAnimal);
        btnCreateCustomAnimal.addActionListener(this::btnCreateCustomAnimal_click);
        gc = Util.setGridBagConstraints(0, 4, 1, 0.3);
        gc.fill = GridBagConstraints.NONE;
        gc.ipady = 10;
        gc.ipadx = 10;
        pnlCenter.add(btnCreateCustomAnimal, gc);
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
            JLabel lblStat = new JLabel(String.format("%s :", statID.toString()));
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
        lstChosenAttacks = new JList<>(listModel);
        lstChosenAttacks.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == 127)
                    btnRemoveAtk_click(null);
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        lstChosenAttacks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstChosenAttacks.setVisibleRowCount(5);
        var gc = Util.setGridBagConstraints(0, 1, 0.7, 1);
        gc.ipady = 0;
        pnlAttacks.add(lstChosenAttacks, gc);

        //info Button
        gc = Util.setGridBagConstraints(1, 0, 0.05, 1);
        gc.insets = new Insets(0, 0, 2, 2);
        JButton btnInfo = new JButton("i");
        btnInfo.addActionListener(this::btnInfo_click);
        pnlAttacks.add(btnInfo, gc);
        gc.gridx++;

        // Validation Button
        gc.weightx = 0.25;
        JButton btnAddAttack = new JButton("Add");
        btnAddAttack.addActionListener(this::btnAddAtk_click);
        ButtonColors.setValidationBtnColor(btnAddAttack);
        pnlAttacks.add(btnAddAttack, gc);

        // Delete Button
        gc.gridy = gc.gridx = 1;
        gc.weightx = 0.3;
        gc.gridwidth = 2;
        JButton btnRemove = new JButton("X");
        ButtonColors.setDeleteBtnColor(btnRemove);
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
     * Randomizes the stats.
     * @param e Click event.
     */
    private void btnRandomStats_click(ActionEvent e) {
        cbxBalanced.setSelected(false);
        for (JSlider slider :
                sliders.values()) {
            slider.setValue(RNG.GenerateNumber(MIN_SLIDERVALUE, MAX_SLIDERVALUE));
        }
    }

    /**
     * Resets every stat slider to their initial value.
     * @param e
     */
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
            JOptionPane.showMessageDialog(this, "The animal already reached its maximal capacity.");
        }
    }

    private void btnInfo_click(ActionEvent actionEvent) {
        AttackEnum attack = (AttackEnum) cboAttacks.getSelectedItem();
        if(attack == null) return;
        controller.openAndFillInfoFrame(this, attack);
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
        if(checkIfFormFilled()){
            Animal animal = controller.validateCustomAnimal(currentPlayer, animalToLoad);
            Util.printCreationConfirmation(animal);
            Util.closeFrame(this);
        }

    }

    /**
     * If there is an animal to display, initializes the components with its data (name, stats, etc.)
     */
    private void loadAnimal() {
        if(animalToLoad == null) return;

        txtName.setText(animalToLoad.getName());

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
            cboAttacks.removeItem(attack.getAttackEnum());
        }

        cboAtkBhv.setSelectedItem(animalToLoad.getAttackBehavior().getAttackBhvEnum());
        cboDefBhv.setSelectedItem(animalToLoad.getDefendBehavior().getDefendBhvEnum());
        cboDieBhv.setSelectedItem(animalToLoad.getDieBehavior().getDieBhvEnum());

    }
    
    /**
     * Verifies that all the information is filled :
     * <br>Nickname MUST be filled
     * <br> stats can stay on their default value but a confirmation is asked if so.
     * <br> Confirmation is asked if less than 5 attacks are added.
     * @return True if everything is correctly filled or if the user confirmed that is ok to them.
     */
    private boolean checkIfFormFilled() {
        if(txtName.getText().equals(DEFAULT_NICKNAME)){
            JOptionPane.showMessageDialog(this, "Please name your animal.");
            return false;
        }

        boolean ok = false;
        for (JSlider slider :
                sliders.values()) {
            if(slider.getValue() != 100){
                ok = true;
                break;
            }
        }
        if(!ok){
            var pane = JOptionPane.showConfirmDialog(this, "Keep all the stats on their default value ?",
                    "Default stats", JOptionPane.YES_NO_OPTION);

            if(pane == JOptionPane.NO_OPTION){
                return false;
            }
        }

        if(listModel.getSize() != 5){
            int room = 5-listModel.getSize();
            var pane = JOptionPane.showConfirmDialog(this, String.format("You can still add %d attack%s. Create animal nonetheless ?", room, room == 1 ? "":"s"),
                    "Default stats", JOptionPane.YES_NO_OPTION);

            if(pane == JOptionPane.NO_OPTION){
                return false;
            }
        }
        return true;
    }
}
