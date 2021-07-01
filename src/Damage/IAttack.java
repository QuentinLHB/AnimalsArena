package Damage;

import Action.Abstract.IAction;
import Animal.IAnimal;

public interface IAttack {
    void performAttack(IAnimal target, int damage);
    String getAttackName();
    int getDamageBase();
    String getDescription();
}
