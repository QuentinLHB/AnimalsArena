package Animal.Abstract;

import Action.Attack.Abstract.IAttack;
import Action.Status.Abstract.IStatus;
import Animal.Concrete.StatID;

import java.util.ArrayList;

public interface IAnimal {

    void setName(String name);

    String getName();

    void canAct(boolean allow);

    /**
     * Performs an attack.
     * @param target Target of the attack.
     * @param attack Attack to peform.
     */
    void attack(IAnimal target, IAttack attack);

    /**
     * Peforms a defense move.
     */
    void defend();

    /**
     * Reacts upon an attack.
     * @param damage Damage inflicted by the foe's attack.
     */
    void attacked(int damage);

    /**
     * Reacts upon being hurt by attack or status effect.
     * @param damage damage taken.
     */
    void hurt(int damage);

    /**
     * Checks if the IAnimal is alive.
     * @return true if alive.
     */
    boolean isAlive();

    /**
     * Add a status to the IAnimal.
     * @param status Status to add.
     */
    void addStatus(IStatus status);

    /**
     * Remove an existing status.
     * @param status Status to remove
     */
    void removeStatus(IStatus status);

    void alterStat(StatID statID, float amount);

    /**
     * Get the max health stat of the animal.
     * @return
     */
    int getMaxHealth(); // TODO Remplacer par un getStats ?

    /**
     * Get the statuses inflicted to the IAnimal.
     * @return
     */
    ArrayList<IStatus> getStatuses();

}