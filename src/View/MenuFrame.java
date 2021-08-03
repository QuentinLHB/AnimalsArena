package View;
import Controler.c_Menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuFrame extends JFrame  {
    private c_Menu controler;

    JPanel panButtons;

    private JLabel lblWelcome;
    private JButton btnPvp;
    private JButton btnPve;
    private JButton btnAIvAI;
    private JButton btnConsult;
    private JButton btnCustomize;


    public MenuFrame(c_Menu controler){
        this.controler = controler;
        initComponents();
        initEvent();
        Util.initFrame(this, "Model.Animal Arena", 500, 600);
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

        // Button PVE
        btnPve = new JButton("<html>Player vs AI : Compose your animal and fight against a computer !</html>");
        panButtons.add(btnPve);

        // Button AIvAI
        btnAIvAI = new JButton("<html>AI vs AI : Spectate a fight between two AI !</html>");
        panButtons.add(btnAIvAI);

        // Button Consult
        btnConsult = new JButton("<html>Consult animals and attacks' statistics.</html>");
        panButtons.add(btnConsult);

        // Button Customize
        btnCustomize = new JButton("<html>Create your custom animal.</html>");
        panButtons.add(btnCustomize);
    }

    private void initEvent(){
        btnPvp.addActionListener(e -> btnPvp_click());
        btnAIvAI.addActionListener(e -> btnAIvAI_click());
        btnPve.addActionListener(e -> btnPve_click());

    }

    private void btnPve_click() {
        pickAnimalAndStartBattle(PlayersEnum.playerA, PlayersEnum.CPUA);
    }

    private void btnAIvAI_click() {
        pickAnimalAndStartBattle(PlayersEnum.CPUA, PlayersEnum.CPUB);
    }

    private void btnPvp_click() {
        pickAnimalAndStartBattle(PlayersEnum.playerA, PlayersEnum.playerB);
    }

    private void pickAnimalAndStartBattle(PlayersEnum p1, PlayersEnum p2){
        new CreationMenuFrame(controler, this, p1, p2);

        if(controler.areAnimalsInitiated()){
            new BattleFrame(controler, p1, p2);
            Util.exit(this);
        }
    }

}
