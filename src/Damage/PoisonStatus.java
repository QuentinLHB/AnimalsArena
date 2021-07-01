package Damage;

import Animal.Animal;
import Animal.IAnimal;

import java.util.ArrayList;

public class PoisonStatus extends Status_Base implements IStatus{

    //Constants
    private final int NB_OF_TURNS = 3;
    private final int FRACTION_OF_MAX_HEALTH = 10;

    public PoisonStatus(IAnimal animal){
        super(animal);
        super.turnsLeft = nbOfTurns;
        super.nbOfTurns = NB_OF_TURNS;
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.POISON;
    }

    /**Consumes the effect : Hurt the animal by a fraction of its HP.*/
    @Override
    public void consumeEffect() {
        animal.hurt(animal.getMaxHealth()/8);
        System.out.println(String.format("%s lost %d HP due to poison.", animal.getName(), animal.getMaxHealth()/8));
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
        super.disappear(this);
        System.out.println("The poison fades out.");
    }


}
