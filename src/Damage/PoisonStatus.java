package Damage;

import Animal.Animal;
import Animal.IAnimal;

import java.util.ArrayList;

public class PoisonStatus implements IStatus{

    StatusID statusID = StatusID.POISON;
    private IAnimal animal;
    private final int FRACTION_OF_MAX_HEALTH = 10;
    private int nbOfTurns = 2;
    private int turnsLeft;

    public PoisonStatus(IAnimal animal){
        this.animal = animal;
        this.turnsLeft = nbOfTurns;
    }

    @Override
    public StatusID getStatusID() {
        return statusID;
    }

    /**Consumes the effect : Hurt the animal by a fraction of its HP.*/
    @Override
    public void consumeEffect() {
        animal.hurt(animal.getMaxHealth()/8);
        System.out.println("The animal lost "+ animal.getMaxHealth()/8 + "HP");
        nbOfTurns--;
        if(nbOfTurns <=0){
            disappear();
        }
    }
    /** Returns the total amount of turns of poison.*/
    @Override
    public int getTurns() {
        return nbOfTurns;
    }

    /**Returns the amount of turns left before the poison fades out.*/
    @Override
    public int getTurnsLeft() {
        return turnsLeft;
    }

    /**Removes the status from the animal.*/
    @Override
    public void disappear() {
        ArrayList<IStatus> statuses = animal.getStatuses();
        statuses.remove(this);
        System.out.println("The poison fades out.");
    }


}
