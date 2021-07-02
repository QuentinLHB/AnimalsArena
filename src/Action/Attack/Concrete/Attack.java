package Action.Attack.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Animal.Abstract.IAnimal;
import Action.Attack.Abstract.IAttack;

public class Attack implements IAttack {

    private final String name;
    private int damageBase;
    private String description;
    IInflictStatus inflictStatus;


    /**
     * Constructor of any attack
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param InflictStatus Concrete InflictStatus from the IInflictStatus interface (if none, use InflictNoStatus class)
     */
    public Attack(String name, int damageBase, IInflictStatus InflictStatus){
        this(name, damageBase);
        this.inflictStatus = InflictStatus;
    }

    public Attack(String name, int damageBase){
        this.name = name;
        this.damageBase = damageBase;
        this.inflictStatus = new InflictNoStatus();
    }

    @Override
    public void performAttack(IAnimal target, int attackStat) {
        if(damageBase > 0){
            target.attacked(damageBase+attackStat);
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
}
