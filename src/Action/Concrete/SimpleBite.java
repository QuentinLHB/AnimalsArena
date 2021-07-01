package Action.Concrete;

import Animal.IAnimal;
import Damage.IAttack;

public class SimpleBite implements IAttack {

    private int damageBase = 20;

    @Override
    public void performAttack(IAnimal target, int damage) {
        target.attacked(damage+damageBase);
    }

    @Override
    public String getAttackName() {
        return "Bite";
    }

    @Override
    public int getDamageBase() {
        return damageBase;
    }

    @Override
    public String getDescription() {
        return String.format("%d dmg | Powerful bite%n", damageBase);
    }
}
