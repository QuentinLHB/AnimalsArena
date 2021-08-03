package Model.Action.Heal;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.IActionBehavior;

public interface IHealBehavior extends IActionBehavior {
    IAttack getAttack();

    void execute();
}
