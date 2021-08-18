package Model.Action.InflictStatus;

import Model.Action.Status.Concrete.StatusID;
import Model.Action.Status.Concrete.Status_Base;
import Model.Animal.Creation.Abstract.IAnimal;

public class SelfInflictStatusBehavior extends InflictStatusBehavior {
    public SelfInflictStatusBehavior(StatusID statusID) {
        super(statusID);
    }

    public SelfInflictStatusBehavior(StatusID statusID, int duration) {
        super(statusID, duration);
    }

    @Override
    public void execute(IAnimal target) {
        super.execute(attack.getAttackOwner());
    }

    @Override
    public String getDescription() {
        return String.format("Applies %s to the user.", getStatusName());
    }

    @Override
    public int score(IAnimal target) {
        if(Status_Base.doesStatusAlreadyExist(target, statusID)){
            return 20;
        }
        return 0;
    }
}
