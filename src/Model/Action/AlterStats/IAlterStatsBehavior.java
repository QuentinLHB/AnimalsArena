package Model.Action.AlterStats;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.IActionBehavior;
import Model.Animal.Creation.Abstract.IAnimal;

public interface IAlterStatsBehavior extends IActionBehavior {
    IAttack getAttack();
    void execute(IAnimal target);
}
