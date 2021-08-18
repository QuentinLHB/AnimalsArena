package Model.Action.Attack.Abstract;

import Model.Action.Attack.Concrete.AttackEnum;
import Model.Action.IActionBehavior;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.ElementType;

import java.io.Serializable;
import java.util.ArrayList;

public interface IAttack extends Serializable {

    void enable(boolean enable);
    boolean isEnabled();

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

    AttackEnum getAttackEnum();

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

    void setType(ElementType type);
    ElementType getType();
}
