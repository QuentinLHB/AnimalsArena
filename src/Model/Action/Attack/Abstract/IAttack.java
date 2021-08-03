package Model.Action.Attack.Abstract;

import Model.Action.IActionBehavior;
import Model.Animal.Creation.Abstract.IAnimal;

import java.io.Serializable;
import java.util.ArrayList;

public interface IAttack extends Serializable {
    /**
     *
     * @return the Model.Animal possessing the attack.
     */
    IAnimal getAttackOwner();

    int getDamageBase();

    /**
     * Peforms the attack.
     * @param target Target of the attack.
     */
    void performAttack(IAnimal target);

    /**
     * Get the attack's name.
     * @return a string of the attack's name.
     */
    String getAttackName();

    /**
     * Get the attack's accuracy.
     * @return A float between 0 and 100.
     */
    float getAccuracy();

    /**
     * Get the description of the attack.
     * @return A string.
     */
    String getDescription();

    ArrayList<IActionBehavior> getBehaviors();
}
