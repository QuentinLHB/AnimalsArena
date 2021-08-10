package View.Frames;

import Controllers.c_Menu;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.AnimalKind;
import Model.Animal.Creation.Concrete.ElementType;
import Model.playerAI.Concrete.Player;
import View.Util;

import javax.swing.*;
import java.awt.*;
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
        JLabel lblTitle = new JLabel("<html> You will now create your own Elemental Model.Animal ! </html>");
        contentPanel.add(lblTitle, BorderLayout.NORTH);
        components.add(lblTitle);

        // Question grid
        JPanel grdQuestion;
        grdQuestion = new JPanel();
        grdQuestion.setLayout(new GridBagLayout());

        // Model.Animal Kind
        grdQuestion.add(new JLabel("<html>Model.Animal (*) : </html>"), setGridBagConstraint(0,0));
        cboAnimalKind = new JComboBox<>(AnimalKind.values());
        grdQuestion.add(cboAnimalKind, setGridBagConstraint(1, 0));
        components.add(cboAnimalKind);

        // Elemental Type
        grdQuestion.add(new JLabel("<html>Elemental Type 1 (*): </html>"), setGridBagConstraint(0, 1));
        cboType1 = new JComboBox<>(ElementType.values());
        grdQuestion.add(cboType1, setGridBagConstraint(1, 1));
        components.add(cboType1);

        // Element Type 2
        grdQuestion.add(new JLabel("<html>Elemental Type 2 (opt.): </html>"), setGridBagConstraint(0, 2));
        cboType2 = new JComboBox<>(ElementType.values());
        grdQuestion.add(cboType2, setGridBagConstraint(1, 2));
        components.add(cboType2);

        //Nickname
        grdQuestion.add(new JLabel("<html>Nickname (opt.): </html>"), setGridBagConstraint(0, 3));
        txtNickname = new JTextField();
        grdQuestion.add(txtNickname, setGridBagConstraint(1, 3));
        components.add(txtNickname);

        // OK Button
        btnOK = new JButton("<html>Create animal</html>");
        GridBagConstraints gc = setGridBagConstraint(0, 4);
        gc.gridwidth = 2;
        grdQuestion.add(btnOK, gc);
        components.add(btnOK);

        contentPanel.add(grdQuestion);
    }

    /**
     * Creates a GridBagConstraint with common property :
     * Fill = BOTH
     * ipady : 5 ; ipadx : 30
     * anchor : WEST
     * @param gridx position in the columns (1 : column 1).
     * @param gridy position in the rows (1 : row 1).
     * @return GridBagConstraint.
     */
    private GridBagConstraints setGridBagConstraint(int gridx, int gridy) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.ipady = 5;
        gc.ipadx = 30;

        gc.anchor = GridBagConstraints.WEST;
        gc.gridx = gridx;
        gc.gridy = gridy;

        return gc;
    }

    private void initEvents(){
        btnOK.addActionListener(e -> btnOk_click());

    }

    private void btnOk_click(){
        IAnimal animal = controller.createAnimal(
                currentPlayer,
                controller.getFoe(currentPlayer),
                (AnimalKind)cboAnimalKind.getSelectedItem(),
                txtNickname.getText(),
                (ElementType)cboType1.getSelectedItem(),
                (ElementType)cboType2.getSelectedItem()

        );

        Util.printCreationConfirmation(animal);

        Util.exit(this);
    }
}
