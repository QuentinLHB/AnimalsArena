package Model.Animal.Behaviors.DieBehavior.Abstract;

import java.io.Serializable;

public interface IDieBehavior extends Serializable {

    void die();

    boolean isAlive();
}
