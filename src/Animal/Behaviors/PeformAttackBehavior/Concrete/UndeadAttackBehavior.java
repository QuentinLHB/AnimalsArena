package Animal.Behaviors.PeformAttackBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;

/**
 * Classe décrivant le comportement d'attaque des Undeads : S'infligent des dégats égaux à la moitié de leur stat d'attaque.
 */
public class UndeadAttackBehavior extends SimpleAttackBehavior implements IPerformAttackBehavior {


    public UndeadAttackBehavior(IAnimal attackingAnimal){
        super(attackingAnimal);
    }

    @Override
    public void attack(IAnimal target, IAttack attack, float attackStat) {
        super.attack(target, attack, attackStat);
        attackingAnimal.hurt(attack.getDamageBase()/2);
    }
}
