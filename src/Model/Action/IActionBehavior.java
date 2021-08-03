package Model.Action;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;

import java.io.Serializable;

/**
 * Interface shared by actions (do damage, heal, inflict status...)
 */
public interface IActionBehavior extends Serializable {
    /**
     *
     * @return description of the action.
     */
    String getDescription();

    /**
     * Sets the attack property.
     * @param attack Attack on which the behavior is applied.
     */
    void setAttack(IAttack attack);

    /**
     * Execute the behavior.
     * @param target Target on which to perform the action.
     */
    void execute(IAnimal target);

    /**
     * Score of the action, used by the AI.
     * @return
     */
    int score(IAnimal target);
}
