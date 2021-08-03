package Model.Animal.Behaviors.PeformAttackBehavior.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;

public class SimpleAttackBehavior implements IPerformAttackBehavior {

    protected IAnimal attackingAnimal;

    public SimpleAttackBehavior(IAnimal attackingAnimal){
        this.attackingAnimal = attackingAnimal;
    }

    /**
     * Peforms the attack if possible.
     * @param target Target of the attack.
     * @param attack Attack used on the target.
     * @param attackStat attack stat of the attacking animal (attack variation applied)
     */
    @Override
    public void attack(IAnimal target, IAttack attack, float attackStat) {
        if(attackingAnimal.canAct()){
            attackingAnimal.setActMode(ActMode.ATTACK);
            if(target.isAlive()){
                System.out.printf("%s performs %s%n", attackingAnimal.getName(), attack.getAttackName());
                attack.performAttack(target);
            }
        }
        else System.out.printf("%s can't act.%n", attackingAnimal.getName());
    }
}
