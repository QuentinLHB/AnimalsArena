package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;

public class SimpleDefendBehavior extends Defend_Base implements IDefendBehavior {


    public SimpleDefendBehavior(IAnimal animal){
        super(animal);
    }
}
