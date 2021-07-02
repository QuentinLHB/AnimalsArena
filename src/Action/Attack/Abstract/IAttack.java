package Action.Attack.Abstract;

import Animal.Abstract.IAnimal;

public interface IAttack {
    void performAttack(IAnimal target, int damage);
    String getAttackName();
    int getDamageBase();
    String getDescription();
}
