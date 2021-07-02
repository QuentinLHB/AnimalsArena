package Action.InflictStatus.Concrete;

import Action.InflictStatus.Abstract.IInflictStatus;
import Animal.Abstract.IAnimal;

public class InflictNoStatus implements IInflictStatus {
    @Override
    public String getStatusName() {
        return null;
    }

    @Override
    public void inflictStatus(IAnimal target) {

    }
}
