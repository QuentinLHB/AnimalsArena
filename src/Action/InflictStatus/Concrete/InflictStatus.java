package Action.InflictStatus.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Concrete.*;
import Animal.Creation.Abstract.IAnimal;

public class InflictStatus implements IInflictStatus {

    private StatusID statusID;
    private int duration;

    public InflictStatus(StatusID statusID, int duration){
        this.statusID = statusID;
        this.duration = duration;
    }

    public InflictStatus(StatusID statusID){
        this(statusID, -1);
    }

    @Override
    public String getStatusName() {
        return statusID.lowerCaseName();
    }

    /**
     * Inflict the status to the target.
     * @param target IAnimal on which to apply the status.
     */
    @Override
    public void inflictStatus(IAnimal target) {
        boolean defaultDuration = duration == -1;
        if (!Status_Base.doesStatusAlreadyExist(target, statusID)) {
            switch (statusID){
                case FEAR -> target.addStatus(defaultDuration ? new FearStatus(target) : new FearStatus(target, duration));

                case SLEEP -> target.addStatus(defaultDuration ? new SleepStatus(target) : new SleepStatus(target, duration));

                case POISON -> target.addStatus(defaultDuration ? new PoisonStatus(target) : new PoisonStatus(target, duration));

                default -> target.addStatus(new NoStatus(target));
            }
        }
    }
}
