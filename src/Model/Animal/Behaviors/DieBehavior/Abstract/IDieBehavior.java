package Model.Animal.Behaviors.DieBehavior.Abstract;

import Model.Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Model.Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Model.Animal.Behaviors.IAnimalBehavior;

import java.io.Serializable;

public interface IDieBehavior extends IAnimalBehavior {

    void die();
    boolean isAlive();
    DieBehaviorEnum getDieBhvEnum();
}
