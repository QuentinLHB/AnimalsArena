package View.Frames;

import Controllers.c_Menu;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Concrete.Animal;
import Model.playerAI.Concrete.Player;
import View.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Frame displaying the previously customized animals and their information.
 */
public class PickCustomizedFrame extends JDialog {

    private c_Menu controller;
    private Player currentPlayer;

    JComboBox<Animal> cboAnimals;
    JLabel lblStats;
    JLabel lblAttacks;
    JLabel lblBehaviors;

    public PickCustomizedFrame(c_Menu controller, JDialog owner, Player currentPlayer){
        super(owner, true);
        this.controller = controller;
        this.currentPlayer = currentPlayer;
        initComponents();
        Util.initFrame(this, "Pick a customized animal", 500, 650);
    }

    private void initComponents() {
        JPanel contentPane = Util.setContentPane(this);

        JPanel pnlCenter = new JPanel(new GridBagLayout());
        contentPane.add(pnlCenter, BorderLayout.NORTH);

        // Selection panel
        JPanel pnlSelection = new JPanel(new GridBagLayout());
        pnlCenter.add(pnlSelection, Util.setGridBagConstraints(0, 0, 1, 1));

            // Label "Existing animals :"
        JLabel lblSelect = new JLabel("Select a saved animal:");
        pnlSelection.add(lblSelect, Util.setGridBagConstraints(0, 0, 0.2,1));

            // Combobox

        cboAnimals = new JComboBox<>(controller.getSavedAnimals());
        pnlSelection.add(cboAnimals, Util.setGridBagConstraints(1, 0, 0.8, 1));
        cboAnimals.addActionListener(this::cboAnimals_itemChanged);


        // Display info panel
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlCenter.add(pnlInfo, Util.setGridBagConstraints(0, 1, 1, 1));

        int i = 0;
            // Stat display (labels)
        pnlInfo.add(createTitleLabel("Stats :"), Util.setGridBagConstraints(0, i++, 1, 0.2));
        lblStats = new JLabel();
        pnlInfo.add(lblStats, Util.setGridBagConstraints(0, i++, 1, 1));

            // Attack display
        pnlInfo.add(createTitleLabel("Attacks :"), Util.setGridBagConstraints(0, i++, 1, 1));
        lblAttacks = new JLabel();
        pnlInfo.add(lblAttacks, Util.setGridBagConstraints(0, i++, 1, 1));

            // Behavior display
        pnlInfo.add(createTitleLabel("Behaviors :"), Util.setGridBagConstraints(0, i++, 1, 1));
        lblBehaviors = new JLabel();
        pnlInfo.add(lblBehaviors, Util.setGridBagConstraints(0, i++, 1, 1));

        // Action Panel
        JPanel pnlAction = new JPanel(new GridBagLayout());
        pnlCenter.add(pnlAction, Util.setGridBagConstraints(0, 2, 1, 1));

            // Confirmation button
        JButton btnConfirmation = new JButton("Pick this animal");
        pnlAction.add(btnConfirmation, Util.setGridBagConstraints(0, 0, 0.5, 1));
        btnConfirmation.addActionListener(this::btnConfirmation_click);

            // Edit button
        JButton btnEdit = new JButton("Edit animal");
        btnEdit.addActionListener(this::btnEdit_click);

        cboAnimals.setSelectedIndex(0);
    }

    private void cboAnimals_itemChanged(ActionEvent e) {
        Animal selectedAnimal = (Animal)cboAnimals.getSelectedItem();
        if(selectedAnimal == null ) return;
        lblStats.setText(selectedAnimal.getHtmlStatDisplay());


        String attackDisplay = "";
        for(IAttack attack :selectedAnimal.getAttacks()) {
            attackDisplay += Util.toBold(attack.getAttackName())+ ": "  + attack.getDescription() + "<br>";
        }
        lblAttacks.setText(Util.toHtml(attackDisplay));

        String behaviorDisplay = "";
        behaviorDisplay += Util.toBold("Attack Behavior:<br>");
        behaviorDisplay += selectedAnimal.getAttackBehavior().getDescription() + "<br>";
        behaviorDisplay += Util.toBold("Defense Behavior:<br>");
        behaviorDisplay += selectedAnimal.getDefendBehavior().getDescription()+ "<br>";
        behaviorDisplay += Util.toBold("Death Behavior:<br>");
        behaviorDisplay += selectedAnimal.getDieBehavior().getDescription()+ "<br>";
        lblBehaviors.setText(Util.toHtml(behaviorDisplay));

    }


    private void btnEdit_click(ActionEvent e) {
    }

    private void btnConfirmation_click(ActionEvent e) {
        controller.newAnimal(currentPlayer, (Animal)cboAnimals.getSelectedItem());
        Util.exit(this);
    }

    private JLabel createTitleLabel(String text){
        JLabel label = new JLabel(Util.toHtml(Util.toUnderlined(text)));
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 14));
        return label;
    }
}
