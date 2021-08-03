package Model.playerAI.Abstract;

import Model.Action.Attack.Abstract.IAttack;

public interface IStrategy {
    IAttack chooseAttack();
    int getStrategyLevel();
}
