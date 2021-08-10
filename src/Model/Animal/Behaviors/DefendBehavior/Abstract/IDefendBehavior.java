package Model.Animal.Behaviors.DefendBehavior.Abstract;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Behaviors.IAnimalBehavior;

import java.io.Serializable;

public interface IDefendBehavior extends IAnimalBehavior {
    void defend(IAttack attack, int damage);
    boolean canDefend();
    void canDefend(boolean allow);
}
