package Model.Action.Heal;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.Attack;
import Model.Animal.Creation.Abstract.IAnimal;
import View.BufferedText;

public class HealPartOfDmgDealt implements IHealBehavior{

    private IAttack attack;
    private float partOfHP;

    /**
     * Constructor of the behavior healing part of the damage dealt by the attack.
     * @param partOfHP Part of HP by which to multiply the damage dealt (ex 0.5).
     */
    public HealPartOfDmgDealt(float partOfHP){
        this.partOfHP = partOfHP;
    }

    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public String getDescription() {
        return String.format("Restores 1/%d of the damage dealt.", Math.round(1/partOfHP));
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }

    @Override
    public void execute(IAnimal target) {
        IAnimal attackOwner = attack.getAttackOwner();
        float amount = Attack.simulateDamage(attack.getAttackOwner(), target, this.attack.getDamageBase()) * partOfHP;
        attackOwner.heal(amount);
        BufferedText.addBufferedText(String.format("%d HP were restored to %s.%n", Math.round(amount), attackOwner));
    }

    @Override
    public int score(IAnimal target) {
        return 0;
    }
}
