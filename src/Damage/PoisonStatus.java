package Damage;

import Animal.Animal;
import Animal.IAnimal;

import java.util.ArrayList;

public class PoisonStatus implements IStatus{

    private IAnimal animal;
    private final int FRACTION_OF_MAX_HEALTH = 10;
    private int nbOfTurns;

    public PoisonStatus(IAnimal animal){
        this.animal = animal;
        this.nbOfTurns = nbOfTurns;
    }


    @Override
    public void consumeEffect() {
        animal.hurt(animal.getMaxHealth()/8);
        nbOfTurns--;
        if(nbOfTurns <=0){
            disappear();
        }
    }

    @Override
    public int getTurnsLeft() {
        return 0;
    }

    @Override
    public void disappear() {
        ArrayList<IStatus> statuses = animal.getStatuses();
        statuses.remove(this);
    }

    @Override
    public int getTurns() {
        return 5;
    }
}
