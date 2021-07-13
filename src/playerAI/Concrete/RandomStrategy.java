package playerAI.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import Util.RNG;
import playerAI.Abstract.IStrategy;

import java.util.ArrayList;

public class RandomStrategy implements IStrategy {

    private PlayerAI player;

    public RandomStrategy(PlayerAI player){
        this.player = player;
    }

    @Override
    public IAttack chooseAttack() {
        IAttack chosenAttack;
        ArrayList<IAttack> attacks = player.getAllyAnimal().getAttacks();
        int rngIndex = RNG.GenerateNumber(0, attacks.size()-1);
        return attacks.get(rngIndex);
    }

    @Override
    public int getStrategyLevel() {
        return 1;
    }
}
