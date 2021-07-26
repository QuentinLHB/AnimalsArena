package Action.DoDamage;

import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Action.DoDamage.IDoDamageBehavior;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;

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
        target.attacked(attack, Math.round(damageBase*(attack.getAttackOwner().getStat(StatID.ATTACK)*attack.getAttackOwner().getStatAlteration(StatID.ATTACK))));
    }

    @Override
    public int score(IAnimal target) {
        if(this.attack == null){
            int a = 1;
        }
        return Attack.simulateDamage(attack.getAttackOwner(), target, attack);
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
