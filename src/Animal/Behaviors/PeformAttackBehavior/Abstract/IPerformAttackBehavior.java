package Animal.Behaviors.PeformAttackBehavior.Abstract;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;

public interface IPerformAttackBehavior {
    void attack(IAnimal target, IAttack attack, float attackStat);
}
