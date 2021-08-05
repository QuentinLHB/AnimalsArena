package Model.Action.Heal;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;
import View.BufferedText;

public class HealFixAmountBehavior implements IHealBehavior{

    private IAttack attack;
    private int amount;

    public HealFixAmountBehavior(int amount){
        this.amount = amount;
    }

    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public String getDescription() {
        return String.format("Heals %dHP to the user.", amount);
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }

    @Override
    public void execute(IAnimal target) {
        IAnimal attackOwner = attack.getAttackOwner();
        attackOwner.heal(amount);
        BufferedText.addBufferedText(String.format("%d HP were restored to %s.%n", amount, attackOwner));
    }

    @Override
    public int score(IAnimal target) {
        IAnimal animal = attack.getAttackOwner();
        float maxHealth = animal.getStat(StatID.MAX_HEALTH)+ animal.getStatAlteration(StatID.MAX_HEALTH);
        if(animal.getHealth() + amount > maxHealth) return Math.round(maxHealth-animal.getHealth());
        return amount;
    }
}
