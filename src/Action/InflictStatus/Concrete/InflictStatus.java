package Action.InflictStatus.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Concrete.*;
import Animal.Creation.Abstract.IAnimal;

public class InflictStatus implements IInflictStatus {

    private StatusID statusID;

    public InflictStatus(StatusID statusID){
        this.statusID = statusID;
    }

    @Override
    public String getStatusName() {
        return statusID.lowerCaseName();
    }

    @Override
    public void inflictStatus(IAnimal target) {
        if (!Status_Base.doesStatusAlreadyExist(target, statusID)) {
            switch (statusID){
                case FEAR -> target.addStatus(new FearStatus(target));
                case SLEEP -> target.addStatus(new SleepStatus(target));
                case POISON -> target.addStatus(new PoisonStatus(target));
                //case PARALYSIS -> target.addStatus(new ParalysisStatus(target));
                default -> target.addStatus(new NoStatus(target));
            }
        }
        }
}
