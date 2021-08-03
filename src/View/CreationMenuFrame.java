package View;
import Controler.c_Menu;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Util.Serialization;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreationMenuFrame extends JDialog {
    private c_Menu controler;
    private PlayersEnum players[];
    private int currentPlayer;
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
     * @param players
     */
    public CreationMenuFrame(c_Menu controler, JFrame owner, PlayersEnum... players){
        super(owner, true);
        this.controler = controler;
        this.players = players;
        currentPlayer = 0;
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
        String title = String.format("<html>How will %s fight ?</html>", players[currentPlayer]);
        lblTitle.setText(title);
    }

    private void initButtons() {
        btnRandomPick = new JButton("<html>Pick a random animal</html>");
        panButtons.add(btnRandomPick);

        btnExistingPick = new JButton("<html>Create an animal based on existing species and types (recommended)</html>");
        panButtons.add(btnExistingPick);

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
        new NewAnimalFrame(controler, this, players[currentPlayer]);
        endPickOption();
    }

    private void btnRandomPick_click(){
        IAnimal animal = controler.createRandomAnimal(players[currentPlayer]);
        JOptionPane.showMessageDialog(null, String.format("New animal created : %s", animal.getName()));
        endPickOption();
    }

    private void endPickOption() {
        currentPlayer++;
        if (currentPlayer == players.length) {
            Util.exit(this);
        }
        else{
            updateLblTitle();
        }
    }


}
