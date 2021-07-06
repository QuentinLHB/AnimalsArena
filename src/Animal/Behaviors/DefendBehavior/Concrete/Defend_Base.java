package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Animal.Creation.Concrete.StatID;

public class Defend_Base implements IDefendBehavior {
    protected IAnimal animal;
    protected boolean canDefend;
    protected static final double ON_DEFENSE_REDUCTION = 0.5;

    protected Defend_Base(IAnimal animal){
        this.animal = animal;
    }

    @Override
    public void defend(IAttack attack, int damage) {
        damage-= (animal.getStats().get(StatID.DEFENSE) * animal.getStatAlterations().get(StatID.DEFENSE));
        if(animal.getActMode().equals(ActMode.DEFENSE)){
            damage *= ON_DEFENSE_REDUCTION;
        }
        animal.hurt(damage);
        System.out.printf("%s lost %d damage.%n%n", animal.getName(), damage);
    }

    @Override
    public boolean canDefend() {
        return canDefend;
    }

    @Override
    public void canDefend(boolean allow) {
        canDefend = allow;
    }
}
