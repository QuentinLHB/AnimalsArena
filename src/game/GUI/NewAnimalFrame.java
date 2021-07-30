package game.GUI;

import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.AnimalKind;
import Animal.Creation.Concrete.ElementType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NewAnimalFrame extends JFrame {
    private Controler controler;
    private PlayersEnum player;

    JComboBox cboAnimalKind;
    JComboBox cboType1;
    JComboBox cboType2;
    JTextField txtNickname;
    JButton btnOK;

    IAnimal animal;

    public NewAnimalFrame(Controler controler, PlayersEnum player){
        this.controler = controler;
        this.player = player;
        initComponents();
        Util.initFrame(this, "Create new animal", 500, 500);
    }

    private void initComponents(){
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // Title label
        contentPanel.add(new JLabel("<html> You will now create your own Elemental Animal ! </html>"), BorderLayout.NORTH);

        // Question grid
        JPanel grdQuestion = new JPanel();
        grdQuestion.setLayout(new GridBagLayout());

        // Animal Kind
        grdQuestion.add(new JLabel("<html>Animal (*) : </html>"), setGridBagConstraint(0,0));
        cboAnimalKind = new JComboBox(AnimalKind.values());
        grdQuestion.add(cboAnimalKind, setGridBagConstraint(1, 0));


        // Elemental Type
        grdQuestion.add(new JLabel("<html>Elemental Type 1 (*): </html>"), setGridBagConstraint(0, 1));
        cboType1 = new JComboBox(ElementType.values());
        grdQuestion.add(cboType1, setGridBagConstraint(1, 1));

        // Element Type 2
        grdQuestion.add(new JLabel("<html>Elemental Type 2 (opt.): </html>"), setGridBagConstraint(0, 2));
        cboType2 = new JComboBox(ElementType.values());
        cboType2.setSize(50, 30);
        grdQuestion.add(cboType2, setGridBagConstraint(1, 2));

        //Nickname
        grdQuestion.add(new JLabel("<html>Nickname (opt.): </html>"), setGridBagConstraint(0, 3));
        txtNickname = new JTextField();
        grdQuestion.add(txtNickname, setGridBagConstraint(1, 3));

        // OK Button
        btnOK = new JButton("<html>Create animal</html>");
        GridBagConstraints gc = setGridBagConstraint(0, 4);
        gc.gridwidth = 2;
        grdQuestion.add(btnOK, gc);

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
//        gc.weightx = 4;
//        gc.weighty = 2;
        gc.gridx = gridx;
        gc.gridy = gridy;

        return gc;
    }

    private void initEvents(){
        btnOK.addActionListener(e -> controler.createAnimal(
                player,
                (AnimalKind)cboAnimalKind.getSelectedItem(),
                txtNickname.getText(),
                (ElementType)cboType1.getSelectedItem())); //todo ajouter 2e type
    }


}
