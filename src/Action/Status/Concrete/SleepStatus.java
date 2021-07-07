package Action.Status.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Creation.Abstract.IAnimal;
import Util.RNG;

public class SleepStatus extends Status_Base implements IStatus {

    public static final int DEFAULT_DURATION = 2;

    public SleepStatus(IAnimal animal){
        this(animal, RNG.GenerateNumber(1, 3));
    }

    public SleepStatus(IAnimal animal, int duration){
        super(animal);
        super.turnsLeft = duration;
        super.duration = duration;
        disableAct();
        printStatusApplication();
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.SLEEP;
    }

    @Override
    public String getStatusName() {
        return StatusID.SLEEP.lowerCaseName();
    }

    @Override
    public void consumeEffect() {
        if(turnsLeft >0){
            disableAct();
            turnsLeft--;
        }
        else disappear();
    }

    private void disableAct(){
        animal.canAct(false);
    }

    @Override
    public void disappear() {
        super.disappear(this);
        animal.canAct(true);
        System.out.println(String.format("%s wakes up !%n", animal.getName()));
    }

    @Override
    public int getDefaultDuration() {
        return DEFAULT_DURATION;
    }

    private void printStatusApplication(){
        System.out.println(String.format("%s falls asleep. zZzZ", animal.getName()));
    }
}
