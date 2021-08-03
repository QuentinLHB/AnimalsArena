package Model.Animal.Behaviors.PeformAttackBehavior.Abstract;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;

import java.io.Serializable;

public interface IPerformAttackBehavior extends Serializable {
    void attack(IAnimal target, IAttack attack, float attackStat);
}
