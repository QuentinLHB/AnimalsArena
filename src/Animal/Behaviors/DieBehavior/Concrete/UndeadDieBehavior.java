package Animal.Behaviors.DieBehavior.Concrete;

import Animal.Abstract.IAnimal;
import Animal.Behaviors.DieBehavior.Abstract.IDieBehavior;

public class UndeadDieBehavior extends SimpleDieBehavior implements IDieBehavior {

    private int livesLeft;
    private int fractionOfHPonRegen;

    public UndeadDieBehavior(IAnimal animal){
        super(animal);
        livesLeft = 2;
        fractionOfHPonRegen = 2;
    }

    @Override
    public void die() {
        if(livesLeft > 0){
            livesLeft--;
            animal.setHealth(animal.getMaxHealth()/fractionOfHPonRegen);
            fractionOfHPonRegen++;
            System.out.println("Je revis !");
        }
        else super.die();
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }
}
