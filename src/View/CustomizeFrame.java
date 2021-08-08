package View;

import Controler.c_Menu;
import Model.Animal.Creation.Concrete.StatID;
import Model.playerAI.Concrete.Player;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Locale;

public class CustomizeFrame extends JDialog {
    private c_Menu controler;
    private Player currentPlayer;

    private JLabel lblTitle;
    private EnumMap<StatID, JSlider> sliders;

    public CustomizeFrame(c_Menu controler, JDialog owner, Player currentPlayer){
        super(owner, true);
        this.controler = controler;
        this.currentPlayer = currentPlayer;
        initComponents();
        Util.initFrame(this, "Customization", 500, 500);
    }

    private void initComponents(){
        JPanel contentPanel = Util.setContentPane(this);

        lblTitle = new JLabel("Create your own custom animal !");
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        // Center panel : owns all the other Panels
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new GridBagLayout());
        contentPanel.add(pnlCenter);

            // Stats : un panel
        JPanel pnlStat = new JPanel();
        pnlStat.setLayout(new GridBagLayout());
        var gc = Util.setGridBagConstraints(0, 0, 1, 0.3);
        pnlCenter.add(pnlStat, gc);
        setStatSliders(pnlStat);

        // Attacks : Un panel
        JPanel pnlAttacks = new JPanel();
        pnlAttacks.setLayout(new GridBagLayout());
        gc = Util.setGridBagConstraints(0, 1, 1, 0.3);
        pnlCenter.add(pnlAttacks, gc);
            // Un combobox pour choisir une attaque
            // Un bouton pour l'ajouter
            // Une liste de 4 attaques

        // Behaviors : un Panel
        JPanel pnlBehaviors = new JPanel();
        pnlBehaviors.setLayout(new GridBagLayout());
        gc = Util.setGridBagConstraints(0, 2, 1, 0.3);
        pnlCenter.add(pnlBehaviors, gc);
    }

    private void setStatSliders(JPanel panel) {
        sliders = new EnumMap<>(StatID.class);

        final int MIN_SLIDERVALUE = 1;
        final int MAX_SLIDERVALUE = 200;
        final int DEFAULT_SLIDERVALUE = 100;

        int row = 0;
        for(StatID statID: StatID.values()){
            if(statID.equals(StatID.ACCURACY)) continue;
            // Stat display
            JLabel lblStat = new JLabel(String.format("%s :", statID.name().toLowerCase(Locale.ROOT)));
            var gc = Util.setGridBagConstraints(0, row, 0.1, 1);
            gc.gridheight = 2;
            panel.add(lblStat, gc);

            // Slider value
            JLabel lblValue = new JLabel(String.valueOf(DEFAULT_SLIDERVALUE));
            gc = Util.setGridBagConstraints(1, row, 0.1, 1);
            panel.add(lblValue, gc);

            // Slider
            JSlider statSlider = new JSlider(MIN_SLIDERVALUE, MAX_SLIDERVALUE, DEFAULT_SLIDERVALUE);
            panel.add(statSlider, Util.setGridBagConstraints(2, row++, 0.8, 1));
            statSlider.setPaintTicks(true);
            statSlider.setMajorTickSpacing(20);
            statSlider.setMinorTickSpacing(5);
            statSlider.addChangeListener(e-> statSlider_valueChange(statSlider, lblValue));
            sliders.put(statID, statSlider);
        }
    }

    private void statSlider_valueChange(JSlider statSlider, JLabel lblValue) {
        lblValue.setText(String.valueOf(statSlider.getValue()));
    }
}
