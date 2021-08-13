package View.Frames;
import Controllers.c_Menu;
import Model.playerAI.Concrete.Player;
import Model.playerAI.Concrete.PlayerAI;
import Model.Util.Position;
import View.Util;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuFrame extends JFrame  {
    private c_Menu controller;

    JPanel panButtons;

    private JButton btnPvp;
    private JButton btnPve;
    private JButton btnAIvAI;
    private JButton btnCustomize;
    private JCheckBox cbxTheme;


    public MenuFrame(c_Menu controller){
        this.controller = controller;
        initComponents();
        initEvent();
        Util.initFrame(this, "Animal Arena - Main Menu", 500, 600);
    }

    private void initComponents(){
        //Window
        JPanel contentPanel = Util.setContentPane(this);

        // Label Welcome
        JLabel lblWelcome = new JLabel("<html>Welcome in Animals Arena, where you can experience RPG-like fights. <br>Please select the mode you'd like :</html>",
                SwingConstants.CENTER);
        lblWelcome.setBorder(new EmptyBorder(0,0,20,0));

        contentPanel.add(lblWelcome, BorderLayout.NORTH);

        // Choice buttons
        panButtons = new JPanel(new GridLayout(4,1, 10, 10));
        initButtons();
        contentPanel.add(panButtons, BorderLayout.CENTER);

        // Checkbox theme
        JPanel southPanel = new JPanel(new GridBagLayout());
        contentPanel.add(southPanel, BorderLayout.SOUTH);
        cbxTheme = new JCheckBox("Dark theme", true);
        cbxTheme.addActionListener(this::cbxTheme_checkChange);
        var gc = Util.setGridBagConstraints(0, 0, 1, 1);
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        southPanel.add(cbxTheme, gc);
    }

    private void cbxTheme_checkChange(ActionEvent actionEvent) {
        if(cbxTheme.isSelected()){
            controller.setTheme("Dark");
        }
        else{
            controller.setTheme("Light");
        }
        SwingUtilities.updateComponentTreeUI(this);
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

        // Button Customize
        btnCustomize = new JButton("<html>Create a fully customized animal.</html>");
        panButtons.add(btnCustomize);
    }

    private void initEvent(){
        btnPvp.addActionListener(e -> btnPvp_click());
        btnAIvAI.addActionListener(e -> btnAIvAI_click());
        btnPve.addActionListener(e -> btnPve_click());
        btnCustomize.addActionListener(e->btnCustomize_click());

    }

    private void btnCustomize_click() {
        new CustomizationMenu(controller, this);
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
        controller.initiatePlayers(p1, p2);
        controller.openCreationMenuFrame();

        if(controller.areAnimalsInitiated()){
            controller.openBattleFrame();
        }
    }

}
