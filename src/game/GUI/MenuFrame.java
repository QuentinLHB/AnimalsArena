package game.GUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuFrame extends JFrame  {
    private Controler controler;

    JPanel panButtons;

    private JLabel lblWelcome;
    private JButton btnPvp;
    private JButton btnPve;
    private JButton btnAIvAI;
    private JButton btnConsult;
    private JButton btnCustomize;


    public MenuFrame(Controler controler){
        this.controler = controler;
        initComponents();
        initEvent();
        Util.initFrame(this, "Animal Arena", 500, 600);
    }

    private void initComponents(){
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // todo set look and feel

        // Label Welcome
        lblWelcome = new JLabel("<html>Welcome in Animals Arena, where you can experience RPG-like fights. <br>Please select the mode you'd like :</html>",
                SwingConstants.CENTER);
        lblWelcome.setBorder(new EmptyBorder(0,0,20,0));

        contentPanel.add(lblWelcome, BorderLayout.NORTH);

        // Choice buttons
        panButtons = new JPanel(new GridLayout(6,1, 10, 10));
        initButtons();
        contentPanel.add(panButtons, BorderLayout.CENTER);

    }

    private void initButtons(){
        // Button PVP
        btnPvp = new JButton("<html>Player vs Player : Each player composes its animal for an intense fight !</html>");

        panButtons.add(btnPvp);
        preferedSize(btnPvp);

        // Button PVE
        btnPve = new JButton("<html>Player vs AI : Compose your animal and fight against a computer !</html>");
        panButtons.add(btnPve);
        preferedSize(btnPve);

        // Button AIvAI
        btnAIvAI = new JButton("<html>AI vs AI : Spectate a fight between two AI !</html>");
        panButtons.add(btnAIvAI);
        preferedSize(btnAIvAI);

        // Button Consult
        btnConsult = new JButton("<html>Consult animals and attacks' statistics.</html>");
        panButtons.add(btnConsult);
        preferedSize(btnConsult);

        // Button Customize
        btnCustomize = new JButton("<html>Create your custom animal.</html>");
        panButtons.add(btnCustomize);
        preferedSize(btnCustomize);
    }

    private void initEvent(){
        btnPvp.addActionListener(e -> {
            new CreationMenuFrame(controler, PlayersEnum.playerA);
            new CreationMenuFrame(controler, PlayersEnum.playerB);

            Util.exit(this);
        });

    }

    private void preferedSize(JButton btn){
//        btn.setPreferredSize(new Dimension(200, 50));
//        btn.setMaximumSize(new Dimension(200, 20));
    }


}
