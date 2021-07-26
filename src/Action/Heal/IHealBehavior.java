package Action.Heal;

import Action.Attack.Abstract.IAttack;
import Action.IActionBehavior;

public interface IHealBehavior extends IActionBehavior {
    IAttack getAttack();

    void execute();
}
