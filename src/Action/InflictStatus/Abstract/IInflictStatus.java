package Action.InflictStatus.Abstract;

import Animal.Abstract.IAnimal;

public interface IInflictStatus {

    String getStatusName();
    void inflictStatus(IAnimal target);
}
