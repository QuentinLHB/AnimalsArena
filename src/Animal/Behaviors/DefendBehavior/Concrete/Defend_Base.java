package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;

public class Defend_Base implements IDefendBehavior {
    protected IAnimal animal;
    protected boolean canDefend;

    protected Defend_Base(IAnimal animal){
        this.animal = animal;
    }

    @Override
    public void defend(IAttack attack, int damage) {

    }

    @Override
    public boolean canDefend() {
        return canDefend;
    }

    @Override
    public void canDefend(boolean allow) {
        canDefend = allow;
    }
}
