package Damage;

import Animal.IAnimal;

public interface IInflictStatus {
    void inflictStatus(IAnimal target, IStatus status, int numberOfTurns);
}
