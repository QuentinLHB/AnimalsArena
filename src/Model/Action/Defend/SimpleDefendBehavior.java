package Model.Action.Defend;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Creation.Abstract.IAnimal;
import View.BufferedText;

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
        IAnimal attackingAnimal = attack.getAttackOwner();
        attackingAnimal.setActMode(ActMode.DEFENSE);
        BufferedText.addBufferedText(attackingAnimal + " is now in Defense Mode.");
    }

    @Override
    public int score(IAnimal target) {
        return 5;
    }
}
