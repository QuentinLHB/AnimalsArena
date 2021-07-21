package Action.Attack.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Action.Status.Concrete.StatusID;
import Animal.Behaviors.DefendBehavior.Concrete.Defend_Base;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Abstract.IAnimal;
import Action.Attack.Abstract.IAttack;
import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.StatID;
import Util.RNG;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Concrete class of a basic attack.
 * It can deal damage, apply status, lower or raise stats, and can be self inflicting.
 */
public class Attack implements IAttack {

    protected final String name;
    protected int damageBase;
    protected float accuracy;
    protected IInflictStatus inflictStatus;
    protected Map<StatID, Float> statsToAlter;
    protected IAnimal attackOwner;
    protected boolean selfInflicting;
    protected String description;
//    protected boolean enabled;


    /**
     * Full Constructor of a damage-based attack.
     * @param attackOwner Animal owning this attack.
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param accuracy Accuracy of the attack (float between 0 and 1)
     * @param inflictStatus Status inflicted by the attack if any.
     *                      If none, InflictNoStatus, or alternate constructor can be used.
     */
    public Attack(IAnimal attackOwner, String name, int damageBase, float accuracy,
                  IInflictStatus inflictStatus, Map<StatID, Float> statsToAlter, boolean selfInflicting,
                  String description){
        this.name = name;
        this.damageBase = damageBase;
        this.inflictStatus = inflictStatus;
        this.accuracy = accuracy;
        this.attackOwner = attackOwner;
        this.selfInflicting = selfInflicting;
        this.statsToAlter = statsToAlter;
        this.description = description;
//        this.enabled = true;
        attackOwner.addAttack(this);
    }

    public Attack(IAnimal attackOwner, String name, int damageBase, float accuracy,
                  IInflictStatus inflictStatus, Map<StatID, Float> statsToAlter, boolean selfInflicting){
        this(attackOwner, name, damageBase, accuracy, inflictStatus, statsToAlter, selfInflicting, null);
    }

    /**
     * Constructor of an attack that doesn't inflict any status or alters any stat, and attacks the opponent only.
     * @param attackOwner
     * @param name
     * @param damageBase
     * @param accuracy
     */
    public Attack(IAnimal attackOwner, String name, int damageBase, float accuracy){
        this(attackOwner, name, damageBase, accuracy, new InflictNoStatus(), null, false, null);
    }

    public Attack(IAnimal attackOwner, String name, int damageBase, float accuracy, IInflictStatus inflictStatus){
        this(attackOwner, name, damageBase, accuracy, inflictStatus, null, false, null);
    }

    public Attack(IAnimal attackOwner, String name, int damageBase, float accuracy, IInflictStatus inflictStatus, String description){
        this(attackOwner, name, damageBase, accuracy, inflictStatus, null, false, description);
    }

    /**
     * Constructor of an attack that alters stats but doesn't inflict status, and can be self-attacking (ex: raise its stats)
     * @param attackOwner
     * @param name
     * @param damageBase
     * @param accuracy
     * @param statsToAlter
     * @param selfInflicting
     */
    public Attack(Animal attackOwner, String name, int damageBase, float accuracy,
                  Map<StatID, Float> statsToAlter, boolean selfInflicting){
        this(attackOwner, name, damageBase, accuracy, new InflictNoStatus(), statsToAlter, selfInflicting);
    }

    public Attack(Animal attackOwner, String name, int damageBase, float accuracy,
                  Map<StatID, Float> statsToAlter, boolean selfInflicting, String description){
        this(attackOwner, name, damageBase, accuracy, new InflictNoStatus(), statsToAlter, selfInflicting, description);
    }

    @Override
    public IAnimal getAttackOwner() {
        return attackOwner;
    }

    @Override
    public void performAttack(IAnimal foe) {
        if(accuracyTest()){
            doDamage(foe);
            alterStats(foe);
            inflictStatus(foe);
        }
        else{
            System.out.println("The attack missed.");
        }
    }

    /**
     * Inflict a status to a target. If the status is self inflicting, the attack owner will be affected.
     * @param foe opposing animal.
     */
    protected void inflictStatus(IAnimal foe) {
        IAnimal statusTarget = inflictStatus.isSelfInflicting() ? attackOwner : foe;
        inflictStatus.inflictStatus(statusTarget);
    }

    /**
     * Alter stat to a target. If the attack is self inflicting, the attack owner will be affected.
     * @param foe opposing animal.
     */
    protected void alterStats(IAnimal foe) {
        IAnimal target = selfInflicting ? attackOwner : foe;
        if(statsToAlter != null){
            for(StatID statToAlter: statsToAlter.keySet()){
                target.alterStat(statToAlter, statsToAlter.get(statToAlter));
            }
        }
    }

    /**
     * Attack a target. If the attack is self inflicting, the attack owner will be damaged.
     * @param foe opposing animal.
     */
    protected void doDamage(IAnimal foe){
        IAnimal target = selfInflicting ? attackOwner : foe;
        if(damageBase > 0){
            target.attacked(this, Math.round(damageBase*(attackOwner.getStat(StatID.ATTACK)*attackOwner.getStatAlteration(StatID.ATTACK))));
        }
    }

    @Override
    public String getAttackName() {
        return name;
    }

    @Override
    public int getDamageBase() {
        return damageBase;
    }

    @Override
    public float getAccuracy() {
        return accuracy;
    }

    /**
     * Generate a description based on the damage base, accuracy, status inflicted, stats altered, and optional description
     * Example : dmg: 10 | accuracy : 100 | Applies fear to the foe | Lower the animal's attack stat by 10% | optional desc
     * @return a string of the generated description.
     */
    @Override
    public String getDescription() {
        String description = "";
        if(damageBase >0){
            description += String.format("dmg: %d", damageBase);
        }

        if(!(description.equals(""))){
            description +=" | ";
        }

        description +=  String.format("accuracy: %d", (int)(accuracy*100));

        if(inflictStatus.getClass() != InflictNoStatus.class){
            String target = inflictStatus.isSelfInflicting() ? "the animal" : "the foe";
            description += String.format(" | Applies %s to %s.", inflictStatus.getStatusName(), target);
        }

        if(statsToAlter != null){
            for (Map.Entry<StatID, Float> stat :
                    statsToAlter.entrySet()) {
                String effect;
                int amount;
                if(stat.getValue() >= 1){
                    effect = "Raises";
                    amount = Math.round((stat.getValue()-1)*100);
                }
                else{
                    effect = "Lower";
                    amount = Math.round((1-stat.getValue())*100);
                }
                String target = selfInflicting ? "the animal" : "the foe";
                description += String.format(" | %s the %s's %s stat by %d%s",
                        effect, target, stat.getKey().name(), amount, "%");
            }
        }

        if(this.description != null){
            description += " | " + this.description;
        }

        return description;
    }

//    @Override
//    public void enabled(boolean enable) {
//        this.enabled = enable;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }

    @Override
    public boolean isSelfInflicting() {
        return selfInflicting;
    }

    @Override
    public StatusID getStatusInflicted() {
        return inflictStatus.getStatusID();
    }

    @Override
    public Map<StatID, Float> getStatAlterations() {
        return statsToAlter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attack attack = (Attack) o;
        return name.equals(attack.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Apply accuracy alterations from the attack and stat alterations to the animal's base accuracy
     * and performs a RNG test to determine if the attack is successful.
     * @return true if the test is successful, else false.
     */
    protected boolean accuracyTest(){
         int accuracyAfterAlterations = Math.round(attackOwner.getStat(StatID.ACCURACY)
                * attackOwner.getStatAlteration(StatID.ACCURACY)
                * accuracy);
        return RNG.RNGsuccess(accuracyAfterAlterations);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Calculate the damages taken by a target by an animal with a specific attack.
     * @param attackingAnimal Attacking animal
     * @param target Target of the attack.
     * @param attack Attack used by the attacking animal.
     * @return Integer representing the amount of damages taken by the foe.
     */
    public static int simulateDamage(IAnimal attackingAnimal, IAnimal target, IAttack attack){
        if(attack.getDamageBase() == 0) return 0;
        float atkStat = attackingAnimal.getStat(StatID.ATTACK);
        float atkVar = attackingAnimal.getStatAlteration(StatID.ATTACK);

        float targetsDef = target.getStat(StatID.DEFENSE);
        float targetsDefVar = target.getStatAlteration(StatID.DEFENSE);

        double defMode = target.getActMode() == ActMode.DEFENSE ? Defend_Base.ON_DEFENSE_REDUCTION : 1;

        //DmgBase*Atk*AtkVar*(1+(1-Def*DefVar))*DefenseMode
        return Math.round((float)(attack.getDamageBase()
                *atkStat*atkVar
                *(1+(1-targetsDef*targetsDefVar)
                * defMode)));
    }
}
