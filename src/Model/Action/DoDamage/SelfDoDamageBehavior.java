package Model.Action.DoDamage;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import View.BufferedText;

public class SelfDoDamageBehavior implements IDoDamageBehavior {

    IAttack attack;
    private int amount;

    public SelfDoDamageBehavior(int amount){
        this.amount = amount;
    }

    @Override
    public IAttack getAttack() {
        return attack;
    }
    @Override
    public void execute(IAnimal foe) {
        IAnimal target = attack.getAttackOwner();
        target.hurt(amount);
        BufferedText.addBufferedText(String.format("%s hits itself.", target));
    }

    @Override
    public int score(IAnimal target) {
        return -amount;
    }

    @Override
    public int getDamageBase() {
        return amount;
    }

    @Override
    public String getDescription() {
        return String.format("Inflicts %d damage to the user", amount);
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
