package Action.Concrete;

import Action.Abstract.IBite;
import Animal.IAnimal;
import Damage.IInflictStatus;
import Damage.IStatus;
import Damage.PoisonStatus;

public class PoisonBite implements IBite, IInflictStatus {
    @Override
    public boolean bite(IAnimal target, int damage) {
        target.hurt(damage);
        System.out.println("The animal performs Poison Bite !");
        IStatus poisonStatus = new PoisonStatus(target);
        inflictStatus(target, poisonStatus, poisonStatus.getTurns());

        return true;
    }

    @Override
    public void doDamage(IAnimal target, int damage) {
        bite(target, damage);
    }

    @Override
    public void inflictStatus(IAnimal target, IStatus status, int numberOfTurns) {
        target.addStatus(status);
    }
}
