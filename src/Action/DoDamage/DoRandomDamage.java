package Action.DoDamage;

import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;
import Util.RNG;

public class DoRandomDamage implements IDoDamageBehavior{
    private final int minDmg;
    private final int maxDmg;
    private IAttack attack;

    public DoRandomDamage(int minDmg, int maxDmg){
        if(maxDmg < minDmg){
            int temp = maxDmg;
            maxDmg = minDmg;
            minDmg = temp;
        }
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
    }

    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public void execute(IAnimal target) {
        target.attacked(attack,
                Math.round(RNG.GenerateNumber(minDmg, maxDmg)
                        *(attack.getAttackOwner().getStat(StatID.ATTACK)
                        *attack.getAttackOwner().getStatAlteration(StatID.ATTACK))));
    }

    @Override
    public int score(IAnimal target) {
        return Attack.simulateDamage(attack.getAttackOwner(), target, attack);
    }

    @Override
    public int getDamageBase() {
        return (maxDmg+minDmg)/2;
    }

    @Override
    public String getDescription() {
        return String.format("dmg : Random between %d & %d", minDmg, maxDmg);
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
