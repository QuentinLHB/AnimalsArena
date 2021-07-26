package Action.AlterStats;

import Action.Attack.Abstract.IAttack;
import Action.IActionBehavior;
import Animal.Creation.Abstract.IAnimal;

public interface IAlterStatsBehavior extends IActionBehavior {
    IAttack getAttack();
    void execute(IAnimal target);
}
