package Animal.Behaviors.DefendBehavior.Abstract;

import Action.Attack.Abstract.IAttack;

public interface IDefendBehavior {
    void defend(IAttack attack, int damage);
    boolean canDefend();
    void canDefend(boolean allow);
}
