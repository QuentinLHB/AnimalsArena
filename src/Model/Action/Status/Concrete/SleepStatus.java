package Model.Action.Status.Concrete;

import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Util.RNG;
import View.BufferedText;

public class SleepStatus extends Status_Base implements IStatus {

    public static final int DEFAULT_DURATION = 2;

    public SleepStatus(IAnimal animal){
        this(animal, RNG.GenerateNumber(1, 3));
    }

    public SleepStatus(IAnimal animal, int duration){
        super(animal, DEFAULT_DURATION);
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
        BufferedText.addBufferedText(String.format("%s wakes up !%n", animal));
    }

    @Override
    public int getDefaultDuration() {
        return DEFAULT_DURATION;
    }

    @Override
    protected void printEffect(String effect) {
        BufferedText.addBufferedText(String.format("%s falls asleep.", animal));
    }

    private void printStatusApplication(){

    }
}
