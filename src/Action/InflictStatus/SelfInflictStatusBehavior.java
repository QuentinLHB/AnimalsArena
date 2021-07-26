package Action.InflictStatus;

import Action.Attack.Abstract.IAttack;
import Action.Status.Concrete.StatusID;
import Action.Status.Concrete.Status_Base;
import Animal.Creation.Abstract.IAnimal;

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
        return String.format("Apply %s to the user.", getStatusName());
    }

    @Override
    public int score(IAnimal target) {
        if(Status_Base.doesStatusAlreadyExist(target, statusID)){
            return 20;
        }
        return 0;
    }
}
