package Model.Action.Defend;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Creation.Abstract.IAnimal;

public class SimpleDefendBehavior implements IDefendBehavior {
    private IAttack attack;

    public SimpleDefendBehavior(){

    }

    @Override
    public String getDescription() {
        return "Puts the animal in defense mode.";
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }

    @Override
    public void execute(IAnimal target) {
        attack.getAttackOwner().setActMode(ActMode.DEFENSE);
    }

    @Override
    public int score(IAnimal target) {
        return 5;
    }
}
