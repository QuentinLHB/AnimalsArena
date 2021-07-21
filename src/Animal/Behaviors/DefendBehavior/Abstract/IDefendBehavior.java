package Animal.Behaviors.DefendBehavior.Abstract;

import Action.Attack.Abstract.IAttack;

import java.io.Serializable;

public interface IDefendBehavior extends Serializable {
    void defend(IAttack attack, int damage);
    boolean canDefend();
    void canDefend(boolean allow);
}
