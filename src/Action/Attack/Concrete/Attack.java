package Action.Attack.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Animal.Creation.Abstract.IAnimal;
import Action.Attack.Abstract.IAttack;
import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.StatID;
import Util.RNG;

import java.util.Map;
import java.util.Objects;

public class Attack implements IAttack {

    private final String name;
    private int damageBase;
    private float accuracy;
    IInflictStatus inflictStatus;
    IAnimal attackOwner;


    /**
     * Full Constructor of a damage-based attack.
     * @param attackOwner Animal owning this attack.
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param accuracy Accuracy of the attack (float between 0 and 1)
     * @param inflictStatus Status inflicted by the attack if any.
     *                      If none, InflictNoStatus, or alternate constructor can be used.
     */
    public Attack(Animal attackOwner, String name, int damageBase, float accuracy, IInflictStatus inflictStatus){
        this.name = name;
        this.damageBase = damageBase;
        this.inflictStatus = inflictStatus;
        this.accuracy = accuracy;
        this.attackOwner = attackOwner;
    }

    /**
     * Constructor missing the attack owner.
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param accuracy Accuracy of the attack (float between 0 and 1)
     * @param inflictStatus Status inflicted by the attack if any.
     *                      If none, InflictNoStatus, or alternate constructor can be used.
     */
    public Attack(String name, int damageBase, float accuracy, IInflictStatus inflictStatus){
        this(null, name, damageBase, accuracy, inflictStatus);
    }

    /**
     * Constructor missing accuracy (1 by default).
     * @param attackOwner Animal owning this attack.
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param inflictStatus Status inflicted by the attack if any.
     *                      If none, InflictNoStatus, or alternate constructor can be used.
     */
    public Attack(Animal attackOwner, String name, int damageBase, IInflictStatus inflictStatus){
        this(attackOwner, name, damageBase, 1f, inflictStatus);
    }

    /**
     * Constructor missing owner and status. A InflictNoStatus is used by default.
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param accuracy Accuracy of the attack (float between 0 and 1)
     */
    public Attack(String name, int damageBase, float accuracy){
        this(null, name, damageBase, accuracy, new InflictNoStatus());
    }

    /**
     * Constructor missing attack owner (none used) and accuracy (1 by default).     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param inflictStatus Status inflicted by the attack if any.
     *                      If none, InflictNoStatus, or alternate constructor can be used.
     */
    public Attack(String name, int damageBase, IInflictStatus inflictStatus){
        this(name, damageBase, 1f, inflictStatus);
    }

    /**
     * Constructor missing attack owner (none used), accuracy (1 by default) and status (none by default).
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     */
    public Attack(String name, int damageBase){
        this(name, damageBase, 1f);
    }



    @Override
    public IAnimal getAttackOwner() {
        return attackOwner;
    }

    @Override
    public void setAttackOwner(IAnimal attackOwner) {
        this.attackOwner = attackOwner;
    }

    @Override
    public void performAttack(IAnimal target, float attackStat) {
        if(accuracyTest()){
            if(damageBase > 0){
                target.attacked(this,Math.round(damageBase*attackStat));
            }
            inflictStatus.inflictStatus(target);
        }
        else{
            System.out.println("The attack missed.");
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

            description += String.format(" | Applies %s", inflictStatus.getStatusName());
        }
        return description;
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
         int accuracyAfterAlterations = Math.round(attackOwner.getStats().get(StatID.ACCURACY)
                * attackOwner.getStatAlterations().get(StatID.ACCURACY)
                * accuracy);
        return RNG.RNGsuccess(accuracyAfterAlterations);
    }
}
