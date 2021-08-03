package Model.Action.DoDamage;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.IActionBehavior;
import Model.Animal.Creation.Abstract.IAnimal;

public interface IDoDamageBehavior extends IActionBehavior {
    IAttack getAttack();

    void execute(IAnimal foe);

    /**
     * Get the damage base of the attack.
     * @return An integer representing the damage base of the attakc.
     */
    int getDamageBase();
}
