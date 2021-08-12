package Model.Animal.Behaviors.DieBehavior.Concrete;

import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.DieBehavior.Abstract.IDieBehavior;

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

    @Override
    public DieBehaviorEnum getDieBhvEnum() {
        return DieBehaviorEnum.SIMPLEDIE_BEHAVIOR;
    }

    @Override
    public String getDescription() {
        return DieBehaviorEnum.SIMPLEDIE_BEHAVIOR.getDescription();
    }
}
