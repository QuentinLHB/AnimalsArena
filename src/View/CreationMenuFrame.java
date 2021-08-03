package View;
import Controler.c_Menu;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Util.Serialization;
import Model.playerAI.Concrete.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreationMenuFrame extends JDialog {
    private c_Menu controler;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    JPanel panButtons;
    JLabel lblTitle;
    JButton btnRandomPick;
    JButton btnExistingPick;
    JButton btnCustomize;
    JButton btnPickCustomized;


    /**
     * Creates the method creation dialog frame (blocks usage of the owner frame)
     * @param controler Controler.
     * @param owner Parent frame, which can't be accessed as long as the creation is open.
     */
    public CreationMenuFrame(c_Menu controler, JFrame owner, Player p1, Player p2){
        super(owner, true);
        this.controler = controler;
        this.p1 = p1;
        this.p2 = p2;
        currentPlayer = p1;
        initComponents();
        initEvents();
        Util.initFrame(this, "Model.Animal Creation", 400, 500);
    }

    private void initComponents() {
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // Label on top

        lblTitle = new JLabel("", SwingConstants.CENTER);
        updateLblTitle();
        lblTitle.setBorder(new EmptyBorder(0,0,20,0));
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        // Buttons
        int nRows = 3;
        if(Serialization.isSaveEmpty()) nRows++;
        panButtons = new JPanel(new GridLayout(nRows, 1, 10, 10));
        initButtons();
        contentPanel.add(panButtons, BorderLayout.CENTER);

    }

    private void updateLblTitle() {
        String title = String.format("<html>How will %s fight ?</html>", currentPlayer);
        lblTitle.setText(title);
    }

    private void initButtons() {

        btnExistingPick = new JButton("<html>Select a standard animal</html>");
        panButtons.add(btnExistingPick);

        btnRandomPick = new JButton("<html>Generate a random animal</html>");
        panButtons.add(btnRandomPick);


        btnCustomize = new JButton("<html>Customize your own animal and stats</html>");
        panButtons.add(btnCustomize);

        if(!Serialization.isSaveEmpty()){
            btnPickCustomized = new JButton("<html>Choose one of the customized animals</html>");
            panButtons.add(btnPickCustomized);
        }

    }

    private void initEvents(){
        btnRandomPick.addActionListener(e-> btnRandomPick_click());

        btnExistingPick.addActionListener(e-> btnPickExistingAnimal_click());
    }

    private void btnPickExistingAnimal_click(){
        new NewAnimalFrame(controler, this, currentPlayer, controler.getFoe(currentPlayer));
        endPickOption();
    }

    private void btnRandomPick_click(){
        IAnimal animal = controler.createRandomAnimal(currentPlayer, controler.getFoe(currentPlayer));
        JOptionPane.showMessageDialog(null, String.format("New animal created : %s", animal.getName()));
        endPickOption();
    }

    private void endPickOption() {
        if (currentPlayer.equals(p2)) {
            Util.exit(this);
        }
        else{
            currentPlayer = p2;
            updateLblTitle();
        }
    }


}
