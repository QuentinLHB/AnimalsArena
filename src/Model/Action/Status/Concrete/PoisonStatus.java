package Model.Action.Status.Concrete;

import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Creation.Abstract.IAnimal;

public class PoisonStatus extends Status_Base implements IStatus {

    //Constants
    private static final int DEFAULT_DURATION = 3;
    private static final float FRACTION_OF_MAX_HEALTH = 0.05f;

    public PoisonStatus(IAnimal animal){
        this(animal, DEFAULT_DURATION);
    }

    public PoisonStatus(IAnimal animal, int duration){
        super(animal, duration);
        super.printEffect("poisoned");
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.POISON;
    }

    @Override
    public String getStatusName() {
        return StatusID.POISON.lowerCaseName();
    }

    /**Consumes the effect : Hurt the animal by a fraction of its HP.*/
    @Override
    public void consumeEffect() {
        if(turnsLeft > 0){
            System.out.println("The poison hurt " + animal.getName());
            animal.hurt(Math.round(animal.getMaxHealth() * FRACTION_OF_MAX_HEALTH));
            turnsLeft--;
        }
        else disappear();

    }

    /**Removes the status from the animal.*/
    @Override
    public void disappear() {
        super.disappear(this);
        System.out.println("The poison fades out.");
    }

    @Override
    public int getDefaultDuration() {
        return DEFAULT_DURATION;
    }


}
