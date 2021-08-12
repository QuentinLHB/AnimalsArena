package Model.Action.DoDamage;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.Attack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;

public class SimpleDoDamageBehavior implements IDoDamageBehavior {

    private IAttack attack;
    private int damageBase;

    public SimpleDoDamageBehavior(int damageBase){
        this.damageBase = damageBase;
    }

    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public void execute(IAnimal target) {
        target.attacked(attack, Math.round(damageBase*(attack.getAttackOwner().getStat(StatID.ATTACK))));
    }

    @Override
    public int score(IAnimal target) {
        return Attack.simulateAttack(attack.getAttackOwner(), target, attack);
    }

    @Override
    public int getDamageBase() {
        return damageBase;
    }

    @Override
    public String getDescription() {
        return String.format("dmg: %d", getDamageBase());
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
