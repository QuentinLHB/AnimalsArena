package Model.Animal.Behaviors.DieBehavior.Concrete;

import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.DieBehavior.Abstract.IDieBehavior;
import Model.Animal.Creation.Concrete.StatID;
import View.BufferedText;

public class UndeadDieBehavior extends SimpleDieBehavior implements IDieBehavior {
    private static final int DEFAULT_LIVES = 2;
    private int livesLeft;
    private int fractionOfHPonRegen;

    public UndeadDieBehavior(IAnimal animal, int lives){
        super(animal);
        livesLeft = lives;
        fractionOfHPonRegen = 2;
    }

    public UndeadDieBehavior(IAnimal animal){
        this(animal, DEFAULT_LIVES);
    }



    @Override
    public void die() {
        if(livesLeft > 0){
            livesLeft--;
            animal.setHealth((float)animal.getStat(StatID.MAX_HEALTH)* animal.getStatAlteration(StatID.MAX_HEALTH)/fractionOfHPonRegen);
            BufferedText.addBufferedText(String.format("%s was reborn with 1/%d of its HP.%n", animal.getName(), fractionOfHPonRegen));
            fractionOfHPonRegen++;
        }
        else super.die();
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }
}
