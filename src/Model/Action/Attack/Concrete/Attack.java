package Model.Action.Attack.Concrete;

import Model.Action.DoDamage.IDoDamageBehavior;
import Model.Action.IActionBehavior;
import Model.Animal.Behaviors.DefendBehavior.Concrete.Defend_Base;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Concrete.ElementType;
import Model.Animal.Creation.Concrete.StatID;
import Model.Util.RNG;
import View.BufferedText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Concrete class of a basic attack.
 * It can deal damage, apply status, lower or raise stats, and can be self inflicting.
 */
public class Attack implements IAttack {

    protected boolean enabled = true;
    protected final AttackEnum attackEnum;
    protected float accuracy;
    protected IAnimal attackOwner;
    private IDoDamageBehavior doDamage;
    private IActionBehavior[] behaviors;
    private ElementType type;

    public Attack(IAnimal attackOwner, AttackEnum attackEnum, float accuracy,
                  IDoDamageBehavior doDamage, IActionBehavior... behaviors) {
        this.attackOwner = attackOwner;
        this.attackEnum = attackEnum;
        this.accuracy = accuracy;
        this.doDamage = doDamage;
        this.behaviors = behaviors;

        doDamage.setAttack(this);
        for (IActionBehavior behavior :
                behaviors) {
            behavior.setAttack(this);
        }
        attackOwner.addAttack(this);
    }


    @Override
    public void enable(boolean enable) {
        this.enabled = enable;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public IAnimal getAttackOwner() {
        return attackOwner;
    }

    @Override
    public int getDamageBase() {
        return doDamage.getDamageBase();
    }

    @Override
    public void performAttack(IAnimal foe) {
        BufferedText.addBufferedText(String.format("%s performs %s.%n", attackOwner.getName(), attackEnum));
        if(accuracyTest()){
            doDamage.execute(foe);
            for(IActionBehavior behavior : behaviors){
                behavior.execute(foe);
            }
        }
        else{
            BufferedText.addBufferedText("The attack missed.");
        }
    }

    @Override
    public AttackEnum getAttackEnum() {
        return attackEnum;
    }

    @Override
    public String getAttackName() {
        return attackEnum.toString();
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
        StringBuilder description = new StringBuilder();
        description.append(doDamage.getDescription());

        if(!description.isEmpty()) description.append(" | ");

        description.append(String.format("accuracy: %d", (int)(accuracy*100)));

        for(IActionBehavior behavior : behaviors){
            description.append(" | ").append(behavior.getDescription());
        }
        return description.toString();
    }

    @Override
    public ArrayList<IActionBehavior> getBehaviors() {
        ArrayList<IActionBehavior> allBehaviors = new ArrayList<>();
        allBehaviors.add(doDamage);
        Collections.addAll(allBehaviors, behaviors);
        return allBehaviors;
    }

    @Override
    public void setType(ElementType type) {
        this.type = type;
    }

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attack attack = (Attack) o;
        return attackEnum.equals(attack.attackEnum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackEnum);
    }

    /**
     * Apply accuracy alterations from the attack and stat alterations to the animal's base accuracy
     * and performs a RNG test to determine if the attack is successful.
     * @return true if the test is successful, else false.
     */
    protected boolean accuracyTest(){
         int accuracyAfterAlterations = Math.round(attackOwner.getStat(StatID.ACCURACY)
                * accuracy);
        return RNG.RNGsuccess(accuracyAfterAlterations);
    }

    @Override
    public String toString() {
        return getAttackName();
    }

    /**
     * Calculate the damages taken by a target by an animal with a specific attack.
     * @param attackingAnimal Attacking animal
     * @param target Target of the attack.
     * @param attack Attack used by the attacking animal.
     * @return Integer representing the amount of damages taken by the foe.
     */
    public static int simulateAttack(IAnimal attackingAnimal, IAnimal target, IAttack attack){
        return simulateDamage(attackingAnimal, target, attack.getDamageBase());
    }

    public static int simulateDamage(IAnimal attackingAnimal, IAnimal target, int dmg){
        if(dmg == 0) return 0;
        float atkStat = attackingAnimal.getStat(StatID.ATTACK);

        float targetsDef = target.getStat(StatID.DEFENSE);

        double defMode = target.getActMode() == ActMode.DEFENSE ? Defend_Base.ON_DEFENSE_REDUCTION : 1;

        //DmgBase*Atk*AtkVar*(1+(1-Def*DefVar))*DefenseMode
        return Math.round((float)(dmg
                *atkStat
                *(1+(1-targetsDef)
                * defMode)));
    }
}
