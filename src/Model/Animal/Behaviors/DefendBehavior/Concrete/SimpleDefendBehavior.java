package Model.Animal.Behaviors.DefendBehavior.Concrete;

import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;

public class SimpleDefendBehavior extends Defend_Base implements IDefendBehavior {


    public SimpleDefendBehavior(IAnimal animal){
        super(animal);
    }

    @Override
    public String getDescription() {
        return DefendBehaviorEnum.SIMPLEDEFEND_BEHAVIOR.getDescription();
    }
}
