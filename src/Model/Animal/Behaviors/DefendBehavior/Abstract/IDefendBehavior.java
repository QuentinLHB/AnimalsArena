package Model.Animal.Behaviors.DefendBehavior.Abstract;

import Model.Action.Attack.Abstract.IAttack;

import java.io.Serializable;

public interface IDefendBehavior extends Serializable {
    void defend(IAttack attack, int damage);
    boolean canDefend();
    void canDefend(boolean allow);
}
