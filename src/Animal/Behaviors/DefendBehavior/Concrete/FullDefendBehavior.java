package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;

public class FullDefendBehavior extends Defend_Base implements IDefendBehavior {

    public FullDefendBehavior(IAnimal animal){
        super(animal);
    }

    @Override
    public void defend(IAttack attack, int damage) {
        System.out.printf("%s took no damage due to its Full Defense ability.", animal.getName());
    }
}
