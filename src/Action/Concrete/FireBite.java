package Action.Concrete;

import Animal.IAnimal;
import Damage.IAttack;

public class FireBite implements IAttack {

    private int damageBase = 15;

    @Override
    public void performAttack(IAnimal target, int damage) {
        target.attacked(damage+damageBase);
    }

    @Override
    public String getAttackName() {
        return "Fire Bite";
    }

    @Override
    public int getDamageBase() {
        return damageBase;
    }

    @Override
    public String getDescription() {
        return String.format("%d dmg", damageBase);
    }
}
