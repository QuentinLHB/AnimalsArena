package Model.Animal.Behaviors.PeformAttackBehavior.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;
import Model.Animal.Creation.Concrete.StatID;
import View.BufferedText;

/**
 * Concrete class of the abstract Attack Behavior interface.
 * User's max HP are lowered for each damaging move, affecting its reincarnation.
 */
public class SelfHarmingAttackBehavior extends SimpleAttackBehavior implements IPerformAttackBehavior {


    public SelfHarmingAttackBehavior(IAnimal attackingAnimal){
        super(attackingAnimal);
    }

    @Override
    public void attack(IAnimal target, IAttack attack, float attackStat) {
        super.attack(target, attack, attackStat);
        if(attack.getDamageBase() > 0){
            attackingAnimal.alterStat(StatID.MAX_HEALTH, 0.95f);
            if(attackingAnimal.getHealth() > attackingAnimal.getMaxHealth()){
                attackingAnimal.setHealth(attackingAnimal.getMaxHealth());
            }
            BufferedText.addBufferedText(String.format("%s's max HP was lowered.%n", attackingAnimal));
        }
    }

    @Override
    public String getDescription() {
        return AttackBehaviorEnum.SELFHARMING_BEHAVIOR.getDescription();
    }
}
