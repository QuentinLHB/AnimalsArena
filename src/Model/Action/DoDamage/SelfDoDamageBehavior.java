package Model.Action.DoDamage;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;

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
        return String.format("Inflict %d dmg to the user");
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
