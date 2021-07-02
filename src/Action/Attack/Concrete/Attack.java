package Action.Attack.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Animal.Abstract.IAnimal;
import Action.Attack.Abstract.IAttack;

public class Attack implements IAttack {

    private final String name;
    private int damageBase;
    private String description;
    IInflictStatus status;


    /**
     * Constructor of any attack
     * @param name Name of the attack.
     * @param damageBase Damage base of the attack.
     * @param status Concrete InflictStatus from the IInflictStatus interface (if none, use InflictNoStatus class)
     */
    public Attack(String name, int damageBase, IInflictStatus status){
        this.name = name;
        this.damageBase = damageBase;
        this.status = status;
    }

    @Override
    public void performAttack(IAnimal target, int attackStat) {
        if(damageBase > 0){
            target.attacked(damageBase+attackStat);
        }
        status.inflictStatus(target);
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

        if(!(status instanceof InflictNoStatus)){
            if(!(description.equals(""))){
                description +=" | ";
            }
            description += String.format("Applies %s", status.getStatusName());

        }

        return description;
    }
}
