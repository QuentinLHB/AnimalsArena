package Action.DoDamage;

import Action.Attack.Abstract.IAttack;
import Action.IActionBehavior;
import Animal.Creation.Abstract.IAnimal;

public interface IDoDamageBehavior extends IActionBehavior {
    IAttack getAttack();

    void execute(IAnimal foe);

    /**
     * Get the damage base of the attack.
     * @return An integer representing the damage base of the attakc.
     */
    int getDamageBase();
}
