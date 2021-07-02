package Action.InflictStatus.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Concrete.PoisonStatus;
import Action.Status.Concrete.StatusID;
import Action.Status.Concrete.Status_Base;
import Animal.Abstract.IAnimal;

public class InflictPoison implements IInflictStatus {


    @Override
    public String getStatusName() {
        return StatusID.POISON.lowerCaseName();
    }

    @Override
    public void inflictStatus(IAnimal target) {
        if (!Status_Base.doesStatusAlreadyExist(target, StatusID.POISON)) {
            target.addStatus(new PoisonStatus(target));
        }
    }
}
