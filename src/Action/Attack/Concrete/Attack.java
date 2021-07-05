package Action.Attack.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Animal.Creation.Abstract.IAnimal;
import Action.Attack.Abstract.IAttack;
import Animal.Creation.Concrete.Animal;

import java.util.Objects;

public class Attack implements IAttack {

    private final String name;
    private int damageBase;
    private String description;
    IInflictStatus inflictStatus;
    IAnimal attackOwner;


    /**
     * Constructor of any attack
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param inflictStatus Concrete InflictStatus from the IInflictStatus interface (if none, use InflictNoStatus class)
     */
    public Attack(String name, int damageBase, IInflictStatus inflictStatus){
        this(name, damageBase);
        this.inflictStatus = inflictStatus;
    }

    public Attack(String name, int damageBase){
        this.name = name;
        this.damageBase = damageBase;
        this.inflictStatus = new InflictNoStatus();
    }

    public Attack(Animal attackOwner, String name, int damageBase, IInflictStatus inflictStatus){
        this(name, damageBase, inflictStatus);
        this.attackOwner = attackOwner;
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
    public void performAttack(IAnimal target, int attackStat) {
        if(damageBase > 0){
            target.attacked(this,damageBase+attackStat);
        }
        inflictStatus.inflictStatus(target);
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
    public String getDescription() {
        String description = "";
        if(damageBase >0){
            description += String.format("%d dmg", damageBase);
        }

        if(!(inflictStatus instanceof InflictNoStatus)){
            if(!(description.equals(""))){
                description +=" | ";
            }
            description += String.format("Applies %s", inflictStatus.getStatusName());

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
}
