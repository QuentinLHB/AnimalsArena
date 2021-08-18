package View;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Concrete.ElementType;

import javax.swing.*;
import java.awt.*;

public class ButtonColors {

    public static void setValidationBtnColor(JButton button){
        setColor(button, new Color(17, 76, 42), Color.WHITE);
    }

    public static void setDeleteBtnColor(JButton button){
        setColor(button, new Color(100, 2, 2), Color.WHITE);
    }

    public static void setEditBtnColor(JButton button){
        setColor(button, new Color(151, 115, 12 ), Color.WHITE);
    }

    private static void setColor(JButton button, Color bg, Color fg){
        button.setBackground(bg);
        button.setForeground(fg);
    }

    public static void setAttackButtonColor(JButtonAttack btnAttack, String theme){
        ElementType type = btnAttack.getAttack().getType();
        if(type == null){
            return;
        }

        if(theme.equals("Dark")){

            switch (type){
                case ELETRIC -> setColor(btnAttack, new Color(165, 165, 54), Color.WHITE);
                case WATER -> setColor(btnAttack, new Color(1, 78, 107), Color.WHITE);
                case POISON -> setColor(btnAttack, new Color(63, 1, 102), Color.WHITE);
                case FIRE -> setColor(btnAttack, new Color(126, 0, 0), Color.WHITE);
                case UNDEAD -> setColor(btnAttack, new Color(40, 2, 60), Color.WHITE);
                default ->{}
            }
        }

        else{
            switch (type){
                case ELETRIC -> setColor(btnAttack, new Color(238, 251, 81), Color.BLACK);
                case WATER -> setColor(btnAttack, new Color(182, 238, 255), Color.BLACK);
                case POISON -> setColor(btnAttack, new Color(188, 161, 224), Color.BLACK);
                case FIRE -> setColor(btnAttack, new Color(252, 77, 74), Color.BLACK);
                case UNDEAD -> setColor(btnAttack, new Color(112, 88, 143), Color.BLACK);
                default -> setColor(btnAttack, new Color(206, 206, 206), Color.BLACK);

            }
        }
    }
}
