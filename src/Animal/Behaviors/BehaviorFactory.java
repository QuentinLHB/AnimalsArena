package Animal.Behaviors;

import Animal.Behaviors.DefendBehavior.Concrete.*;
import Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Animal.Behaviors.DieBehavior.Concrete.SimpleDieBehavior;
import Animal.Behaviors.DieBehavior.Concrete.UndeadDieBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Animal.Behaviors.PeformAttackBehavior.Concrete.SimpleAttackBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.UndeadAttackBehavior;
import Animal.Creation.Concrete.Animal;

public class BehaviorFactory {


    public static void addBehaviors(Animal animal, AttackBehaviorEnum attackBehavior, DefendBehaviorEnum defendBehavior, DieBehaviorEnum dieBehavior){
        switch (attackBehavior){
            case UNDEAD_BEHAVIOR -> animal.setAttackBehavior(new UndeadAttackBehavior(animal));
            default -> animal.setAttackBehavior(new SimpleAttackBehavior(animal));
        }
        switch (defendBehavior){
            case NODEFEND_BEHAVIOR -> animal.setDefendBehavior(new NoDefendBehavior(animal));
            case FULLDEFEND_BEHAVIOR -> animal.setDefendBehavior(new FullDefendBehavior(animal));
            case MIRRORDEFEND_BEHAVIOR -> animal.setDefendBehavior(new MirrorDefenseBehavior(animal, 2));
            case HEALDEFND_BEHAVIOR -> animal.setDefendBehavior(new HealDefendBehavior(animal));
            default -> animal.setDefendBehavior(new SimpleDefendBehavior(animal));
        }

        switch (dieBehavior){
            case UNDEADDIE_BEHAVIOR -> animal.setDieBehavior(new UndeadDieBehavior(animal));
            default -> animal.setDieBehavior(new SimpleDieBehavior(animal));
        }
    }

}
