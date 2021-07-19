package Action.Attack.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;

import java.util.Map;

/**
 * Concrete class of the IAttack interface.
 * Used to create moves that can heal
 */
public class HealingMove extends Attack{
    private float healingBase;

    public HealingMove(IAnimal attackOwner, String name, int damageBase, float healingBase, float accuracy, String description) {
        super(attackOwner, name, damageBase, accuracy, new InflictNoStatus(), null, false, description);
        this.healingBase = healingBase;
    }

    public HealingMove(IAnimal attackOwner, String name, int damageBase, float accuracy, String description){
        this(attackOwner, name, damageBase, 1, accuracy, description);
    }
    public HealingMove(IAnimal attackOwner, String name, int damageBase, float accuracy){
        this(attackOwner, name, damageBase, 1, accuracy, null);
    }

    public HealingMove(IAnimal attackOwner, String name, float healingBase, float accuracy, String description){
        this(attackOwner, name, 0, healingBase, accuracy, description);
    }

    public HealingMove(IAnimal attackOwner, String name, float healingBase, float accuracy){
        this(attackOwner, name, 0, healingBase, accuracy, null);
    }

    /**
     * Attacks the foe and drains half of the damage dealt.
     * @param foe
     */
    @Override
    public void performAttack(IAnimal foe) {
        if(accuracyTest()){
            doDamage(foe);

            int health = attackOwner.getHealth();
            int amount = 0;
            if(damageBase > 0){
                amount = health+(int)(Attack.simulateDamage(attackOwner, foe, this)/2);
                attackOwner.setHealth(amount);
            }

            if(healingBase != 1){
                amount = Math.round(attackOwner.getStat(StatID.MAX_HEALTH)*healingBase);
                attackOwner.setHealth(health+ amount);
            }

            System.out.printf("%d HP were restored to %s%n", amount, attackOwner.getName());
        }
    }

    @Override
    public String getDescription() {
        String description = super.getDescription();
        if(damageBase > 0)
            description += " | Heals half of the damage dealt.";
        if(healingBase != 1)
            description += String.format(" | Restores %d%s HP", Math.round(healingBase*100), "%");
        return description;
    }
}
