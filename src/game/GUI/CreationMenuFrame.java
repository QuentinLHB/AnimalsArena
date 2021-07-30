package game.GUI;
import game.Serialization;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;

public class CreationMenuFrame extends JFrame {
    private Controler controler;
    private PlayersEnum player;
    JPanel panButtons;
    JLabel lblDescription;
    JButton btnRandomPick;
    JButton btnExistingPick;
    JButton btnCustomize;
    JButton btnPickCustomized;


    public CreationMenuFrame(Controler controler, PlayersEnum player){
        this.controler = controler;
        this.player = player;
        initComponents();
        initEvents();
        Util.initFrame(this, "Animal Creation", 400, 500);
    }

    private void initComponents() {
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // Label on top
        lblDescription = new JLabel("<html>How will %s fight ?</html>", SwingConstants.CENTER);
        lblDescription.setBorder(new EmptyBorder(20,0,20,0));
        contentPanel.add(lblDescription);

        // Buttons
        int nRows = 3;
        if(Serialization.isSaveEmpty()) nRows++;
        panButtons = new JPanel(new GridLayout(nRows, 1, 10, 10));
        initButtons();
        contentPanel.add(panButtons);

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
        btnRandomPick.addActionListener(e-> {
            new BattleFrame(controler);
            Util.exit(this);
        });

        btnExistingPick.addActionListener(e->{
            new NewAnimalFrame(controler, player);
            Util.exit(this);
        });
    }


}
