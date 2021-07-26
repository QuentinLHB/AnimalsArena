package Action.InflictStatus;

import Action.Attack.Abstract.IAttack;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;

/**
 * Concrete class of the InflictStatus abstract.
 * Used if the attack inflicts no status.
 */
public class InflictNoStatusBehavior implements IInflictStatusBehavior {

    IAttack attack;

    public InflictNoStatusBehavior() {
    }
    @Override
    public String getStatusName() {
        return null;
    }

    @Override
    public void execute(IAnimal target) {

    }

    @Override
    public int score(IAnimal target) {
        return 0;
    }

    @Override
    public StatusID getStatusID() {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
