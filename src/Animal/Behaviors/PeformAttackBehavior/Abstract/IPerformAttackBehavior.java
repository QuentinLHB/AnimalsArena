package Animal.Behaviors.PeformAttackBehavior.Abstract;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;

import java.io.Serializable;

public interface IPerformAttackBehavior extends Serializable {
    void attack(IAnimal target, IAttack attack, float attackStat);
}
