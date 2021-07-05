package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;

public class NoDefendBehavior extends Defend_Base implements IDefendBehavior {

    public NoDefendBehavior(IAnimal animal){
        super(animal);
    }

    @Override
    public void defend(IAttack attack, int damage){
        animal.hurt(damage);
    }
}
