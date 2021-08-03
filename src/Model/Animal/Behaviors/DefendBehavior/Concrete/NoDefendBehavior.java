package Model.Animal.Behaviors.DefendBehavior.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;

public class NoDefendBehavior extends Defend_Base implements IDefendBehavior {

    public NoDefendBehavior(IAnimal animal){
        super(animal);
    }

    @Override
    public void defend(IAttack attack, int damage){
        animal.hurt(damage);
    }
}
