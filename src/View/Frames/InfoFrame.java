package View.Frames;

import Controllers.c_Menu;
import Model.Action.Attack.Concrete.AttackEnum;
import Model.Animal.Creation.Concrete.Animal;
import Model.Animal.Creation.Concrete.AnimalKind;
import Model.Animal.Creation.Concrete.ElementType;
import Model.Util.Position;
import View.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class InfoFrame extends JDialog {

    private c_Menu controller;
    JComboBox<Info> cboInfo;

    JPanel pnlInfoDisplay;

    public enum Info {
        GENERAL("General info"),
        ANIMALS("Animals' stats"),
        TYPES("Types' stats"),
        ATTACKS("Attacks' stats");

        Info(String desc){
            this.desc = desc;
        }

        final String desc;

        @Override
        public String toString() {
            return desc;
        }
    }

    public InfoFrame(c_Menu controller, JFrame owner){
        super(owner, true);
        init(controller);
    }
    public InfoFrame(c_Menu controller, JDialog owner){
        super(owner, true);
        init(controller);

    }

    private void init(c_Menu controller){
        this.controller = controller;
        initComponents();
        Util.initFrame(this, "Info", 500, 400);
    }

    private void initComponents() {
        JPanel contentPane = Util.setContentPane(this);

        // Center pane
        JPanel pnlCenter = new JPanel(); //new GridLayout(2, 0)
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.PAGE_AXIS));
        contentPane.add(pnlCenter, BorderLayout.CENTER);

            // General choice pane
        JPanel pnlGeneralChoice = new JPanel(new FlowLayout());
        pnlCenter.add(pnlGeneralChoice);
        pnlGeneralChoice.add(new JLabel("Consult: "));
        cboInfo = new JComboBox<>(Info.values());
        pnlGeneralChoice.add(cboInfo);

            // Info display : filled when an item is selected in the combobox.
        pnlInfoDisplay = new JPanel(new GridBagLayout());
        pnlCenter.add(pnlInfoDisplay);

        // Events
        cboInfo.addItemListener(this::cboInfo_itemChanged);
        initCombo(cboInfo);

    }

    private void cboInfo_itemChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.DESELECTED){
            Component[] components = pnlInfoDisplay.getComponents();
            for (Component component : components) {
                pnlInfoDisplay.remove(component);
            }
            this.revalidate();//for swing components
            this.repaint();//sometimes required, sometimes not, safest to include
        }

        if(e.getStateChange() == ItemEvent.SELECTED){
            Info info = (Info)cboInfo.getSelectedItem();
            if(info == null) info = Info.GENERAL;
            switch (info){
                case ANIMALS -> showAnimalsInfo();
                case TYPES -> showTypeInfo();
                case ATTACKS -> showAttacksInfo();
                default -> showGeneralInfo();
            }
        }
    }

    private void showGeneralInfo() {
        String generalInfo = """
                            <html>
                            Each animal has <b>stats</b> : Attack, Defense, Speed...<br>
                            Each <b>elemental type</b> makes these stats vary.<br>
                            Some <b>attack</b> and <b>statuses</b> make these stats vary too.<br>
                            <br>
                            Damage taken is based on :<br>
                            <ul>
                                <li> the animal's <b>attack stat</b> (influenced by its kind and type(s))<br>
                                <li> the attack's <b>damage base</b><br>
                                <li> the foe's <b>defense</b><br>
                                <li> the foe's <b>action mode</b> (Attack/Defense)
                            </ul>
                            Ex: Animal (1.1Atk, 0.9 Atk Variation) performs Bite (15 DMG)<br>
                            The foe (1.3 Def) is in Defense Mode :<br>
                            DmgBase * Atk * AtkVar * (1+(1-Defense)) * DefenseMode = 15*1.1*0.9*0.7*0.5 = <b>5</b>. <br>
                            Roundings along the way may produce a 1dmg difference.
                            </html>
                            """;
        JLabel lbl = new JLabel(generalInfo);
        pnlInfoDisplay.add(lbl, Util.setGridBagConstraints(0, 0, 1, 1));
    }

    private void showAttacksInfo() {

        JComboBox<AttackEnum> cboAttack;
        cboAttack = new JComboBox<>(AttackEnum.getSortedAttacks());
        var gc = Util.setGridBagConstraints(0, 0, 1, 1);
        gc.insets = new Insets(0, 10, 0, 10);
        pnlInfoDisplay.add(cboAttack, gc);
        JLabel lblDescription = new JLabel();
        gc.gridy = 1;
        pnlInfoDisplay.add(lblDescription, gc);
        cboAttack.addItemListener(e -> cboAttack_itemChanged(e, lblDescription));
        initCombo(cboAttack);
    }

    private void cboAttack_itemChanged(ItemEvent e, JLabel lblDescription) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            JComboBox<AttackEnum> cbo = (JComboBox<AttackEnum>)e.getSource();
            AttackEnum chosenAttack = (AttackEnum)cbo.getSelectedItem();
            if(chosenAttack == null) return;
            lblDescription.setText(Util.toHtml(
                    String.format("%s: <br>%s",
                            Util.toBold(chosenAttack.toString()),
                            chosenAttack.getDescription()
                    )));
        }
    }

    private void showAnimalsInfo() {
        JComboBox<AnimalKind> cboAnimals = new JComboBox<>(AnimalKind.values());
        var gc = Util.setGridBagConstraints(0, 0, 1, 1);
        gc.insets = new Insets(0, 0, 0, 0);
        gc.ipady = 0;

        pnlInfoDisplay.add(cboAnimals, gc);
        gc.gridy++;

        JLabel lblIcon = new JLabel();
        pnlInfoDisplay.add(lblIcon, gc);
        gc.gridy++;

        JLabel lblDesc = new JLabel();
        pnlInfoDisplay.add(lblDesc, gc);
        gc.gridy++;

        JLabel lblStats = new JLabel();
        pnlInfoDisplay.add(lblStats, gc);

        cboAnimals.addItemListener(e -> cboAnimals_itemChanged(e, lblIcon, lblDesc, lblStats));
        initCombo(cboAnimals);

    }

    private void cboAnimals_itemChanged(ItemEvent e, JLabel lblIcon, JLabel lblDesc, JLabel lblStats) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            JComboBox<AnimalKind> cbo = (JComboBox<AnimalKind>)e.getSource();
            AnimalKind animalKind = (AnimalKind) cbo.getSelectedItem();
            if(animalKind == null) return;

            // Animal image
            ImageIcon img = new ImageIcon(controller.getUrl(animalKind));
            lblIcon.setIcon(img);

            //Animal description
           lblDesc.setText(Util.toHtml(animalKind.getDescription()));

            // Animal stats
            String display = String.format("Stats:<br>Health : %d <br>Attack: %d <br>Defense: %d<br>Speed: %d<br><br>",
                    Math.round(animalKind.getMaxHealth()),
                    Math.round(animalKind.getAttack() * 100),
                    Math.round(animalKind.getDefense() * 100),
                    Math.round(animalKind.getSpeed() * 100)
            );
            lblStats.setText(Util.toHtml(display));

        }
    }

    private void showTypeInfo() {

        JComboBox<ElementType> cboType = new JComboBox<>(ElementType.values());
        pnlInfoDisplay.add(cboType, Util.setGridBagConstraints(0, 0, 1, 1));
        JLabel lblTypeDisplay = new JLabel();
        pnlInfoDisplay.add(lblTypeDisplay, Util.setGridBagConstraints(0, 1, 1, 1));
        cboType.addItemListener(e -> cboType_itemChanged(e, lblTypeDisplay));

        String desc = """ 
                Type statistics are applied to the animal kind's stats : <br>
                Each animal's stat is multiplied by each of its type's stat multiplier. (ie : dog's health x water multiplier: 100x1.3=130)
                """;
        JLabel lblDesc = new JLabel(Util.toHtml(desc));
        pnlInfoDisplay.add(lblDesc, Util.setGridBagConstraints(0, 2, 1, 1));
        initCombo(cboType);

    }

    private void cboType_itemChanged(ItemEvent e, JLabel lblTypeDisplay) {

        if(e.getStateChange() == ItemEvent.SELECTED){
            JComboBox<ElementType> cbo = (JComboBox<ElementType>)e.getSource();
            ElementType type = (ElementType) cbo.getSelectedItem();
            if(type == null) return;
            String display = String.format("%s: <br>Health : %d <br>Attack: %d <br>Defense: %d<br>Speed: %d<br><br>",
                    Util.toBold(type.toString()),
                    Math.round(type.getHealthVariation() * 100),
                    Math.round(type.getAttackVariation() * 100),
                    Math.round(type.getDefenseVariation() * 100),
                    Math.round(type.getSpeedVariation() * 100)

            );
            lblTypeDisplay.setText(Util.toHtml(display));
        }

    }

    private void initCombo(JComboBox cboAnimals) {
        cboAnimals.setSelectedItem(null);
        cboAnimals.setSelectedIndex(0);
    }
}
