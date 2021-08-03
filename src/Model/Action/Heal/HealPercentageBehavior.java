package Model.Action.Heal;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;

public class HealPercentageBehavior implements IHealBehavior{

    private IAttack attack;
    private float amount;

    public HealPercentageBehavior(float amount){
        this.amount = amount;
    }
    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public void execute() {
        attack.getAttackOwner().heal(amount);
    }

    @Override
    public void execute(IAnimal animal) {
        execute();
    }

    @Override
    public int score(IAnimal target) {
        IAnimal animal = attack.getAttackOwner();
        float maxHealth = animal.getStat(StatID.MAX_HEALTH)+ animal.getStatAlteration(StatID.MAX_HEALTH);
        float HPHealed = maxHealth*(amount-1);
        if(animal.getHealth() +  HPHealed > maxHealth) return Math.round(maxHealth - animal.getHealth());
        return Math.round(HPHealed);
    }

    @Override
    public String getDescription() {
        return String.format("Restores %d%s HP to the user", Math.round(amount*100), "%");
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
