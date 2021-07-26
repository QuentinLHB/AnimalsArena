package Action.InflictStatus;

import Action.Attack.Abstract.IAttack;
import Action.Status.Concrete.*;
import Animal.Creation.Abstract.IAnimal;

/**
 * Concrete of the InflictStatus abstract?
 *
 */
public class InflictStatusBehavior implements IInflictStatusBehavior {

    protected IAttack attack;
    protected StatusID statusID;
    protected int duration;
//    private boolean selfInflicting;


    public InflictStatusBehavior(StatusID statusID){
        this(statusID, -1);
    }

    /**
     * Full constructor of InflictStatus.
     * @param statusID Status enumerator.
     * @param duration
     */
    public InflictStatusBehavior(StatusID statusID, int duration){
        this.statusID = statusID;
        this.duration = duration;
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
    public void execute(IAnimal target) {
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
    public int score(IAnimal target) {
        if(!Status_Base.doesStatusAlreadyExist(target, statusID)){
            if(duration == -1) return 15;
            else return 3*duration;
        }
        return 0;
    }

    @Override
    public StatusID getStatusID() {
        return statusID;
    }

    @Override
    public String getDescription() {
        return String.format("Applies %s to the foe.", getStatusName());
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
