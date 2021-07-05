package Animal.Behaviors.PeformAttackBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Abstract.IAnimal;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;

import java.util.Objects;

public class SimpleAttackBehavior implements IPerformAttackBehavior {

    protected IAnimal attackingAnimal;
    protected String behaviorID;


    public SimpleAttackBehavior(IAnimal attackingAnimal){
        this.attackingAnimal = attackingAnimal;
        this.behaviorID = "SimpleAttackBehavior";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleAttackBehavior that = (SimpleAttackBehavior) o;
        return behaviorID.equals(that.behaviorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(behaviorID);
    }

    //
//    @Override
//    public void canAct(boolean allow) {
//        attackingAnimal.canAct(allow);
//    }
}
