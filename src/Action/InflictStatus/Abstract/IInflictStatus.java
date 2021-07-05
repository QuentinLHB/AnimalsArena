package Action.InflictStatus.Abstract;

import Animal.Creation.Abstract.IAnimal;

public interface IInflictStatus {

    String getStatusName();
    void inflictStatus(IAnimal target);
}
