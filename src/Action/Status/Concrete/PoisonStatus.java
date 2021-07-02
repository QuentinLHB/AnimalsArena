package Action.Status.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Abstract.IAnimal;

public class PoisonStatus extends Status_Base implements IStatus {

    //Constants
    private final int NB_OF_TURNS = 3;
    private final int FRACTION_OF_MAX_HEALTH = 10;

    public PoisonStatus(IAnimal animal){
        super(animal);
        super.turnsLeft = NB_OF_TURNS;
        super.nbOfTurns = NB_OF_TURNS;
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.POISON;
    }

    @Override
    public String getStatusName() {
        return "poison";
    }

    /**Consumes the effect : Hurt the animal by a fraction of its HP.*/
    @Override
    public void consumeEffect() {
        if(turnsLeft > 0){
            animal.hurt(animal.getMaxHealth()/FRACTION_OF_MAX_HEALTH);
            System.out.println(String.format("%s lost %d HP due to poison.%n", animal.getName(), animal.getMaxHealth()/8));
            nbOfTurns--;
        }
        else disappear();

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
