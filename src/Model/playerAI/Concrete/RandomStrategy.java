package Model.playerAI.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Util.RNG;
import Model.playerAI.Abstract.IStrategy;

import java.util.ArrayList;

public class RandomStrategy implements IStrategy {

    private PlayerAI player;

    public RandomStrategy(PlayerAI player){
        this.player = player;
    }

    @Override
    public IAttack chooseAttack() {
        ArrayList<IAttack> attacks = player.getAllyAnimal().getAttacks();
        int rngIndex = RNG.GenerateNumber(0, attacks.size()-1);
        return attacks.get(rngIndex);
    }

    @Override
    public int getStrategyLevel() {
        return 1;
    }
}
