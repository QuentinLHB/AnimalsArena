package View;

import Model.Action.Attack.Abstract.IAttack;

import javax.swing.*;
import java.awt.*;

public class JButtonAttack extends JButton {
    IAttack attack;

    public JButtonAttack(IAttack attack){
        super(attack.getAttackName());
        this.attack = attack;
//        super.setFont(new Font(super.getFont().getName(), Font.BOLD, 12));
    }

    public IAttack getAttack(){
        return attack;
    }

    @Override
    public void setEnabled(boolean b) {
        if(b){
            super.setEnabled(attack.isEnabled());
        }
        else super.setEnabled(b);
    }
}
