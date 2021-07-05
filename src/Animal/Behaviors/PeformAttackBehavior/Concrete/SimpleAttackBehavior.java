package Animal.Behaviors.PeformAttackBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;

public class SimpleAttackBehavior implements IPerformAttackBehavior {

    protected IAnimal attackingAnimal;

    public SimpleAttackBehavior(IAnimal attackingAnimal){
        this.attackingAnimal = attackingAnimal;
    }

    @Override
    public void attack(IAnimal target, IAttack attack, int attackStat) {
        if(attackingAnimal.canAct()){
            attackingAnimal.setActMode(ActMode.ATTACK);
            if(target.isAlive()){
                System.out.printf("%s performs %s%n", attackingAnimal.getName(), attack.getAttackName());
                attack.performAttack(target, attackStat);
            }
        }
    }
}
