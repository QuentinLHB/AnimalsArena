package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;

public class SimpleDefendBehavior extends Defend_Base implements IDefendBehavior {
    private static final double ON_DEFENSE_REDUCTION = 0.5;
    private boolean canDefend;

    public SimpleDefendBehavior(IAnimal animal){
        super(animal);
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
}
