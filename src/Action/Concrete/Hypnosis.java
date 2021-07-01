package Action.Concrete;

import Animal.IAnimal;
import Damage.*;

public class Hypnosis implements IAttack, IInflictStatus {
    @Override
    public void inflictStatus(IAnimal target) {
        if(!Status_Base.doesStatusAlreadyExist(target, StatusID.SLEEP)){
            new SleepStatus(target);
        }
    }

    @Override
    public void performAttack(IAnimal target, int damage) {
        inflictStatus(target);
    }

    @Override
    public String getAttackName() {
        return "Hypnosis";
    }

    @Override
    public int getDamageBase() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Applies Sleep to the foe.";
    }
}
