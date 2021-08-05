package Model.Animal.Creation.Abstract;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Creation.Concrete.StatID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public interface IAnimal extends Serializable {

    void setName(String name);
    String getName();

    void setActMode(ActMode actMode);
    ActMode getActMode();

    int getHealth();
    void setHealth(float health);
    float heal(float amount);
    int heal(int amount);

    void canAct(boolean allow);
    boolean canAct();

    void canDefend(boolean allow);
    boolean canDefend();

    ArrayList<IAttack> getAttacks();
    void addAttack(IAttack attack);
    void removeAttack(IAttack attack);

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

    /**
     * Get a clone of the attacks' stats.
     * @return Dictionary containing all the stats.
     */
    Map<StatID, Float> getStats();

    /**
     * Get a specific stat.
     * @param statID StatID of the attack.
     * @return float representing the stat.
     */
    Float getStat(StatID statID);
    void alterStat(StatID statID, float amount);
//    Map<StatID, Float> getStatAlterations();
    Float getStatAlteration(StatID statID);

    /**
     * Get the max health stat of the animal.
     * @return
     */
    int getMaxHealth();

    /**
     * Get the statuses inflicted to the IAnimal.
     * @return
     */
    ArrayList<IStatus> getStatuses();

}
