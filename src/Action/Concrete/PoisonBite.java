package Action.Concrete;

import Action.Abstract.IBite;
import Animal.IAnimal;
import Damage.*;

import java.util.ArrayList;

public class PoisonBite implements IDoDamage, IInflictStatus {


    @Override
    public void doDamage(IAnimal target, int damage) {
        target.hurt(damage);
        System.out.println("The animal performs Poison Bite !");
        IStatus poisonStatus = new PoisonStatus(target);
        inflictStatus(target, poisonStatus, poisonStatus.getTurns());

    }

    @Override
    public void inflictStatus(IAnimal target, IStatus status, int numberOfTurns) {
        ArrayList<IStatus> statuses = target.getStatuses();
        boolean exists = false;
        for (IStatus existingStatus:statuses) {
            if(existingStatus.getStatusID().equals(StatusID.POISON))exists = true;
        }
        if(!exists) target.addStatus(status);
    }
}
