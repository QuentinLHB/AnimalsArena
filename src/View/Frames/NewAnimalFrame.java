package View.Frames;

import Controllers.c_Menu;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.AnimalKind;
import Model.Animal.Creation.Concrete.ElementType;
import Model.playerAI.Concrete.Player;
import View.Util;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class NewAnimalFrame extends JDialog {
    private c_Menu controller;
//    private Player playerA;
//    private Player playerB;

    private Player currentPlayer;
    private ArrayList<JComponent> components = new ArrayList<>();

    JComboBox cboAnimalKind;
    JComboBox cboType1;
    JComboBox cboType2;
    JTextField txtNickname;
    JButton btnOK;

    public NewAnimalFrame(c_Menu controller, JDialog owner, Player currentPlayer){
        super(owner, true);
        this.controller = controller;
        this.currentPlayer = currentPlayer;
        initComponents();
        initEvents();
        Util.initFrame(this, "Create new animal", 500, 500);
    }

    private void initComponents(){
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // Title label
        JLabel lblTitle = new JLabel("<html>Choose your animal kind and types. </html>");
        contentPanel.add(lblTitle, BorderLayout.NORTH);
        components.add(lblTitle);

        // Question grid
        JPanel grdQuestion = new JPanel();
        grdQuestion.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 15));
        grdQuestion.setLayout(new GridBagLayout());

        // GridBag Constraints
        var gcLabel = setGridBagConstraint(0, 0);
        var gcField = setGridBagConstraint(1, 0);
        var gcInfo = setGridBagConstraint(2, 0);
        gcInfo.weightx=0.1;

        // Animal Kind
        grdQuestion.add(new JLabel("<html>Animal:</html>"), gcLabel);
        cboAnimalKind = new JComboBox<>(AnimalKind.values());
        grdQuestion.add(cboAnimalKind, gcField);
        components.add(cboAnimalKind);
        JButton btnAnimalInfo = new JButton("i");
        grdQuestion.add(btnAnimalInfo, gcInfo);
        btnAnimalInfo.addActionListener(this::btnAnimalInfo_click);

        // Elemental Type
        incrementGridBagConstraintsRow(gcLabel, gcField, gcInfo);
        grdQuestion.add(new JLabel("<html>Elemental Type 1: </html>"), gcLabel);
        cboType1 = new JComboBox<>(ElementType.values());
        grdQuestion.add(cboType1, gcField);
        components.add(cboType1);
        JButton btnTypeInfo = new JButton("i");
        grdQuestion.add(btnTypeInfo, gcInfo);
        btnTypeInfo.addActionListener(e-> btnTypeInfo_click(e, cboType1));

        // Element Type 2
        incrementGridBagConstraintsRow(gcLabel, gcField, gcInfo);
        grdQuestion.add(new JLabel("<html>Elemental Type 2: </html>"), gcLabel);
        cboType2 = new JComboBox<>(ElementType.values());
        grdQuestion.add(cboType2, gcField);
        components.add(cboType2);
        JButton btnType1Info = new JButton("i");
        grdQuestion.add(btnType1Info, gcInfo);
        btnType1Info.addActionListener(e-> btnTypeInfo_click(e, cboType2));

        //Nickname
        incrementGridBagConstraintsRow(gcLabel, gcField, gcInfo);
        grdQuestion.add(new JLabel("<html>Nickname (opt.): </html>"), gcLabel);
        txtNickname = new JTextField();
        grdQuestion.add(txtNickname, gcField);
        components.add(txtNickname);

        // OK Button
        btnOK = new JButton("<html>Create animal</html>");
        GridBagConstraints gc = setGridBagConstraint(0, 4);
        gc.gridwidth = 2;
        grdQuestion.add(btnOK, gc);
        components.add(btnOK);

        contentPanel.add(grdQuestion);
    }

    private void btnTypeInfo_click(ActionEvent e, JComboBox cboType) {
        controller.openAndFillInfoFrame(this, (ElementType) cboType.getSelectedItem());
    }

    private void btnAnimalInfo_click(ActionEvent e) {
        controller.openAndFillInfoFrame(this, (AnimalKind) cboAnimalKind.getSelectedItem());
    }

    /**
     * Creates a GridBagConstraint with common property :
     * Fill = BOTH
     * ipady : 5 ; ipadx : 30
     * anchor : WEST
     * @param column position in the columns (1 : column 1).
     * @param row position in the rows (1 : row 1).
     * @return GridBagConstraint.
     */
    private GridBagConstraints setGridBagConstraint(int column, int row) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.ipady = 5;
        gc.ipadx = 15;

        gc.anchor = GridBagConstraints.WEST;
        gc.gridx = column;
        gc.gridy = row;
        gc.weightx = 0.45;


        return gc;
    }

    private void incrementGridBagConstraintsRow(GridBagConstraints... constraints){
        for(GridBagConstraints gc : constraints){
            gc.gridy++;
        }
    }

    private void initEvents(){
        btnOK.addActionListener(e -> btnOk_click());

    }

    private void btnOk_click(){
        IAnimal animal = controller.newAnimal(
                currentPlayer,
                txtNickname.getText(),
                (AnimalKind)cboAnimalKind.getSelectedItem(),
                (ElementType)cboType1.getSelectedItem(),
                (ElementType)cboType2.getSelectedItem()

        );

        Util.printCreationConfirmation(animal);

        Util.closeFrame(this);
    }
}
