package Model.Animal.Behaviors.DefendBehavior.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Model.Animal.Creation.Concrete.StatID;

public class Defend_Base implements IDefendBehavior {
    protected IAnimal animal;
    protected boolean canDefend = true;
    public static final double ON_DEFENSE_REDUCTION = 0.5;

    protected Defend_Base(IAnimal animal){
        this.animal = animal;
    }

    @Override
    public void defend(IAttack attack, int damage) {
        float defStat = (animal.getStat(StatID.DEFENSE) * animal.getStatAlteration(StatID.DEFENSE));
        damage = Math.round(damage*(1+(1-defStat)));
        if(animal.getActMode().equals(ActMode.DEFENSE)){
            damage *= ON_DEFENSE_REDUCTION;
        }

        animal.hurt(damage);

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
