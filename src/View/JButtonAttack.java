package View;

import Model.Action.Attack.Abstract.IAttack;

import javax.swing.*;

public class JButtonAttack extends JButton {
    IAttack attack;

    public JButtonAttack(IAttack attack){
        super(attack.getAttackName());
        this.attack = attack;
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
