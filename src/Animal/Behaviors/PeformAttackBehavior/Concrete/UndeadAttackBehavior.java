package Animal.Behaviors.PeformAttackBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;
import Animal.Creation.Concrete.StatID;

/**
 * Concrete class of the abstract Attack Behavior interface.
 * User's max HP are lowered for each damaging move, affecting its reincarnation.
 */
public class UndeadAttackBehavior extends SimpleAttackBehavior implements IPerformAttackBehavior {


    public UndeadAttackBehavior(IAnimal attackingAnimal){
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
        }
    }
}
