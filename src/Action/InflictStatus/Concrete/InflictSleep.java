package Action.InflictStatus.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Concrete.SleepStatus;
import Action.Status.Concrete.StatusID;
import Action.Status.Concrete.Status_Base;
import Animal.Abstract.IAnimal;

public class InflictSleep implements IInflictStatus {
    @Override
    public String getStatusName() {
        return StatusID.SLEEP.lowerCaseName();
    }

    @Override
    public void inflictStatus(IAnimal target) {
        if (!Status_Base.doesStatusAlreadyExist(target, StatusID.SLEEP)) {
            target.addStatus(new SleepStatus(target));
        }
    }
}
