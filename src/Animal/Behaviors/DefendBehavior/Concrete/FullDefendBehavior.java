package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Animal.Creation.Concrete.StatID;
import Util.RNG;

/**
 * Concrete behavior of the abstract Defense Behavior interface.
 * Allows the animal to take no damage upon defense.
 */
public class FullDefendBehavior extends Defend_Base implements IDefendBehavior {
    private static final int SUCCESS_RATE = 60;

    public FullDefendBehavior(IAnimal animal){
        super(animal);
    }

    @Override
    public void defend(IAttack attack, int damage) {

        if(animal.getActMode().equals(ActMode.DEFENSE) && RNG.RNGsuccess(SUCCESS_RATE)){
            System.out.printf("%s took no damage due to its Full Defense ability.%n", animal.getName());
        }
        else{
            super.defend(attack, damage);
        }
    }
}
