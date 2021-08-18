package Model.Animal.Behaviors.DefendBehavior.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Creation.Abstract.IAnimal;
import View.BufferedText;

public class HealDefendBehavior extends Defend_Base implements IDefendBehavior {
    public HealDefendBehavior(IAnimal animal) {
        super(animal);
    }

    @Override
    public void defend(IAttack attack, int damage) {
        super.defend(attack, damage);
        if(animal.getActMode().equals(ActMode.DEFENSE)) {
            animal.heal(1.05f);
            BufferedText.addBufferedText(String.format("%s recovered some HP due to its Healing Defense ability%n", animal.getName()));
        }
    }

    @Override
    public DefendBehaviorEnum getDefendBhvEnum() {
        return DefendBehaviorEnum.HEALDEFND_BEHAVIOR;
    }

    @Override
    public String getDescription() {
        return DefendBehaviorEnum.HEALDEFND_BEHAVIOR.getDescription();
    }
}
