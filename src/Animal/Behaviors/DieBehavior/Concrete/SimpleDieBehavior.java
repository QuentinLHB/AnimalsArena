package Animal.Behaviors.DieBehavior.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Abstract.IAnimal;
import Animal.Behaviors.DieBehavior.Abstract.IDieBehavior;

import java.io.ObjectInputFilter;
import java.util.ArrayList;

public class SimpleDieBehavior implements IDieBehavior {
    protected boolean isAlive;
    protected IAnimal animal;

    public SimpleDieBehavior(IAnimal animal){
        this.animal = animal;
        isAlive = true;
    }
    @Override
    public void die() {
        animal.setHealth(0);
        ArrayList<IStatus> statuses = animal.getStatuses();
        for(var i=0; i < statuses.size(); i++){
            animal.removeStatus(statuses.get(i));
        }
        isAlive = false;
        animal.canAct(false);
        System.out.printf("%s is dead.%n", animal.getName());
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
