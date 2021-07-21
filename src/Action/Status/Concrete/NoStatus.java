package Action.Status.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Creation.Abstract.IAnimal;

public class NoStatus extends Status_Base implements IStatus {

    public NoStatus(IAnimal animal) {
        super(animal, 0);
        disappear();
    }

    @Override
    public StatusID getStatusID() {
        return null;
    }

    @Override
    public String getStatusName() {
        return null;
    }

    @Override
    public void consumeEffect() {
        disappear();
    }

    @Override
    public void disappear() {
        super.disappear(this);
    }

    @Override
    public int getDefaultDuration() {
        return 0;
    }

}
