package Action.Abstract;

import Animal.IAnimal;
import Damage.IDoDamage;

public interface IBite extends IDoDamage {
    boolean bite(IAnimal target, int damage);
}
