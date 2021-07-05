package Animal.Behaviors.PeformAttackBehavior.Abstract;

import Action.Attack.Abstract.IAttack;
import Animal.Abstract.IAnimal;

public interface IPerformAttackBehavior {
    void attack(IAnimal target, IAttack attack, int attackStat);
}
