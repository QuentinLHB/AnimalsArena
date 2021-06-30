package Action.Concrete;

import Action.Abstract.*;
import Animal.IAnimal;

public class SimpleBite implements IBite {



    @Override
    public boolean bite(IAnimal target, int damage) {
        target.hurt(damage);
        System.out.println("The animal peforms Simple Bite !");
        return true;
    }

    @Override
    public void doDamage(IAnimal target, int damage) {
        bite(target, damage);
    }
}
