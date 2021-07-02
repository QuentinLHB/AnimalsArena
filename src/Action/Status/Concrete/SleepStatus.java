package Action.Status.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Abstract.IAnimal;

public class SleepStatus extends Status_Base implements IStatus {

    private final int NB_OF_TURNS = 2;

    public SleepStatus(IAnimal animal){
        super(animal);
        super.turnsLeft = NB_OF_TURNS;
        super.nbOfTurns = NB_OF_TURNS;
        System.out.println(String.format("%s falls asleep. zZzZ", animal.getName()));
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.SLEEP;
    }

    @Override
    public String getStatusName() {
        return "sleep";
    }

    @Override
    public void consumeEffect() {
        if(turnsLeft >0){
            animal.canAct(false);
            turnsLeft--;
        }
        else disappear();

    }

    @Override
    public int getTurnsLeft() {
        return turnsLeft;
    }

    @Override
    public void disappear() {
        super.disappear(this);
        animal.canAct(true);
        System.out.println(String.format("%s wakes up !%n", animal.getName()));
    }

    @Override
    public int getTurns() {
        return 0;
    }
}
