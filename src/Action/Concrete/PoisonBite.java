package Action.Concrete;

import Animal.IAnimal;
import Damage.*;

public class PoisonBite implements IAttack, IInflictStatus {

    private int damageBase= 10;
    @Override
    public void performAttack(IAnimal target, int damage) {
        target.attacked(damage+damageBase);

        inflictStatus(target);
    }

    @Override
    public String getAttackName() {
        return "Poison Bite";
    }

    @Override
    public int getDamageBase() {
        return damageBase;
    }

    @Override
    public String getDescription() {
        return String.format("%d dmg | Inflicts poison.", damageBase);
    }

    @Override
    public void inflictStatus(IAnimal target) {
        if(!Status_Base.doesStatusAlreadyExist(target, StatusID.POISON)){
            target.addStatus(new PoisonStatus(target));
        }
    }
}
