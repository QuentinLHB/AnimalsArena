package Action.InflictStatus.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Concrete.*;
import Animal.Creation.Abstract.IAnimal;

/**
 * Concrete of the InflictStatus abstract?
 *
 */
public class InflictStatus implements IInflictStatus {

    private StatusID statusID;
    private int duration;
    private boolean selfInflicting;

    public InflictStatus(StatusID statusID, int duration){
        this(statusID, duration, false);
    }

    public InflictStatus(StatusID statusID){
        this(statusID, -1);
    }

    /**
     * Full constructor of InflictStatus.
     * @param statusID Status enumerator.
     * @param duration
     * @param selfInflicting
     */
    public InflictStatus(StatusID statusID, int duration, boolean selfInflicting){
        this.statusID = statusID;
        this.duration = duration;
        this.selfInflicting = selfInflicting;
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

                case PARALYSIS -> target.addStatus(defaultDuration ? new ParalysisStatus(target) : new ParalysisStatus(target, duration));

                default -> target.addStatus(new NoStatus(target));
            }
        }
        else System.out.printf("%s is already under %s status%n", target.getName(), statusID.lowerCaseName());
    }

    @Override
    public boolean isSelfInflicting() {
        return selfInflicting;
    }

    @Override
    public StatusID getStatusID() {
        return statusID;
    }
}
