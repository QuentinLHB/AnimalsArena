package Action.Attack.Abstract;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Abstract.IStatus;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;

import java.util.Map;

public interface IAttack {
    /**
     *
     * @return the Animal possessing the attack.
     */
    IAnimal getAttackOwner();

//    void setAttackOwner(IAnimal attackOwner);

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
     * Get the damage base of the attack.
     * @return An integer representing the damage base of the attakc.
     */
    int getDamageBase();

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

//    /**
//     * Enable or disable the attack.
//     * @param enable True to enable, false to disable.
//     */
//    void enabled(boolean enable);
//
//    /**
//     *
//     * @return true if the attack is enabled.
//     */
//    boolean isEnabled();

    /**
     *
     * @return True if the attack is to be inflicted to the attack owner.
     */
    boolean isSelfInflicting();


    /**
     * StatusID (enum) inflicted by the attack.
     * @return
     */
    StatusID getStatusInflicted();

    /**
     * Stats lowered or raised by the attack.
     * @return A dictionary associating the stat altered and the amount of alteration (float)
     */
    Map<StatID, Float> getStatAlterations();
}
