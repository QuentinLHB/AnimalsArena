package Animal.Creation.Abstract;

import Action.Attack.Abstract.IAttack;
import Action.Status.Abstract.IStatus;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Concrete.StatID;

import java.util.ArrayList;
import java.util.Map;

public interface IAnimal {

    void setName(String name);
    String getName();

    void setActMode(ActMode actMode);
    ActMode getActMode();

    int getHealth();
    void setHealth(int health);

    void canAct(boolean allow);
    boolean canAct();

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
    void attacked(IAttack attack, int damage);

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

    Map<StatID, Integer> getStats();
    void alterStat(StatID statID, float amount);
    Map<StatID, Float> getStatAlterations();

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
