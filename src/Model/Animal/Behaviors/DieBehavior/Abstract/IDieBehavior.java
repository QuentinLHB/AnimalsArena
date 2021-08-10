package Model.Animal.Behaviors.DieBehavior.Abstract;

import Model.Animal.Behaviors.IAnimalBehavior;

import java.io.Serializable;

public interface IDieBehavior extends IAnimalBehavior {

    void die();

    boolean isAlive();
}
