package Action.InflictStatus.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;

public class InflictNoStatus implements IInflictStatus {
    @Override
    public String getStatusName() {
        return null;
    }

    @Override
    public void inflictStatus(IAnimal target) {

    }

    @Override
    public boolean isSelfInflicting() {
        return false;
    }

    @Override
    public StatusID getStatusID() {
        return null;
    }
}
