package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Abstract.IAnimal;

public class HealDefendBehavior extends Defend_Base implements IDefendBehavior {
    public HealDefendBehavior(IAnimal animal) {
        super(animal);
    }

    @Override
    public void defend(IAttack attack, int damage) {
        super.defend(attack, damage);
        if(animal.getActMode().equals(ActMode.DEFENSE)) {
            animal.heal(1.1f);
            System.out.printf("%s recovered some HP due to its Healing Defense ability%n", animal.getName());
        }
    }
}
