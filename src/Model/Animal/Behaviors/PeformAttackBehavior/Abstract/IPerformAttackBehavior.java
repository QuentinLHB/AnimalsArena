package Model.Animal.Behaviors.PeformAttackBehavior.Abstract;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Behaviors.IAnimalBehavior;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Model.Animal.Creation.Abstract.IAnimal;

import java.io.Serializable;

public interface IPerformAttackBehavior extends IAnimalBehavior {
    void attack(IAnimal target, IAttack attack, float attackStat);
    AttackBehaviorEnum getAttackBhvEnum();
}
