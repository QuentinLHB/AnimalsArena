package Action.Status.Abstract;

import Action.Status.Concrete.StatusID;

public interface IStatus {

    StatusID getStatusID();
    String getStatusName();
    void consumeEffect();
    int getTurnsLeft();
    void disappear();
    int getDuration();
    int getDefaultDuration();
}
