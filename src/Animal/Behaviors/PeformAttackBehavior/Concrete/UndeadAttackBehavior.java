package Animal.Behaviors.PeformAttackBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Abstract.IAnimal;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;

public class UndeadAttackBehavior extends SimpleAttackBehavior implements IPerformAttackBehavior {


    public UndeadAttackBehavior(IAnimal attackingAnimal){
        super(attackingAnimal);
        behaviorID = "UndeadAttackBehavior";
    }

    @Override
    public void attack(IAnimal target, IAttack attack, int attackStat) {
        super.attack(target, attack, attackStat);
        // todo rajouter un comportement particulier à l'undead.
    }
//
//    @Override
//    public void canAct(boolean allow) {
//
//    }
}
