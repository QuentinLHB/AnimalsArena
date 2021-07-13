package Action.InflictStatus.Abstract;

import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;

public interface IInflictStatus {


    String getStatusName();
    void inflictStatus(IAnimal target);
    boolean isSelfInflicting();
    StatusID getStatusID();
}
