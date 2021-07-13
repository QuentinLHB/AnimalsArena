package playerAI.Abstract;

import Action.Attack.Abstract.IAttack;

public interface IStrategy {
    IAttack chooseAttack();
    int getStrategyLevel();
}
