package View.Frames;
import Controllers.c_Menu;
import Model.playerAI.Concrete.Player;
import Model.playerAI.Concrete.PlayerAI;
import Model.Util.Position;
import View.Util;

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
        Util.initFrame(this, "Animal Arena - Main Menu", 500, 600);
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
        Player p1 = new Player(Position.BOTTOM);
        PlayerAI p2 = new PlayerAI(Position.TOP);
        pickAnimalAndStartBattle(p1, p2);
    }

    private void btnAIvAI_click() {
        PlayerAI p1 = new PlayerAI(Position.BOTTOM);
        PlayerAI p2 = new PlayerAI(Position.TOP);
        pickAnimalAndStartBattle(p1, p2);
    }

    private void btnPvp_click() {
        Player p1 = new Player(Position.BOTTOM);
        Player p2 = new Player(Position.TOP);
        pickAnimalAndStartBattle(p1, p2);
    }

    private void pickAnimalAndStartBattle(Player p1, Player p2){
        controler.initiatePlayers(p1, p2);
        controler.openCreationMenuFrame();

        if(controler.areAnimalsInitiated()){
            controler.openBattleFrame();
        }
    }

}
