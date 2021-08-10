package View.Frames;

import Controllers.c_Menu;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Concrete.Animal;
import Model.playerAI.Concrete.Player;
import View.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        Util.initFrame(this, "Pick a customized animal", 500, 500);
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
        cboAnimals.addActionListener(e->cboAnimals_itemChanged(e));


        // Display info panel
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlCenter.add(pnlInfo, Util.setGridBagConstraints(0, 1, 1, 1));

            // Stat display (labels)
        lblStats = new JLabel();
        pnlInfo.add(lblStats, Util.setGridBagConstraints(0, 0, 1, 1));

            // Attack display
        lblAttacks = new JLabel();
        pnlInfo.add(lblAttacks, Util.setGridBagConstraints(0, 1, 1, 1));

            // Behavior display
        lblBehaviors = new JLabel();
        pnlInfo.add(lblBehaviors, Util.setGridBagConstraints(0, 2, 1, 1));

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
    }

    private void cboAnimals_itemChanged(ActionEvent e) {
        Animal selectedAnimal = (Animal)cboAnimals.getSelectedItem();
        if(selectedAnimal == null ) return;
        lblStats.setText(Util.toHtml(selectedAnimal.getStatDisplay()));


        String attackDisplay = "";
        for(IAttack attack :selectedAnimal.getAttacks()) {
            attackDisplay += attack.getAttackName() + ": " + attack.getDescription() + "<br>";
        }
        lblAttacks.setText(Util.toHtml(attackDisplay));

        String behaviorDisplay = "";
        behaviorDisplay += selectedAnimal.getAttackBehavior().getDescription();
        behaviorDisplay += selectedAnimal.getDefendBehavior().getDescription();
        behaviorDisplay += selectedAnimal.getDieBehavior().getDescription();
        lblBehaviors.setText(Util.toHtml(behaviorDisplay));

    }


    private void btnEdit_click(ActionEvent e) {
    }

    private void btnConfirmation_click(ActionEvent e) {

    }
}
