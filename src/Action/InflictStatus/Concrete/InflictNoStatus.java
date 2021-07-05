package Action.InflictStatus.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Animal.Creation.Abstract.IAnimal;

public class InflictNoStatus implements IInflictStatus {
    @Override
    public String getStatusName() {
        return null;
    }

    @Override
    public void inflictStatus(IAnimal target) {

    }
}
