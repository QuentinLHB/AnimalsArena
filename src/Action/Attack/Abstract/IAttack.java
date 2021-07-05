package Action.Attack.Abstract;

import Animal.Creation.Abstract.IAnimal;

public interface IAttack {
    IAnimal getAttackOwner();
    void setAttackOwner(IAnimal attackOwner);
    void performAttack(IAnimal target, int damage);
    String getAttackName();
    int getDamageBase();
    String getDescription();
}
