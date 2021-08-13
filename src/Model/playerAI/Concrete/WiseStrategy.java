package Model.playerAI.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.Attack;
import Model.Action.IActionBehavior;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Util.RNG;
import Model.playerAI.Abstract.IStrategy;

import java.util.*;

/**
 * Choose an attack based on a score calculated by taking into account :<br>
 * - Damages dealt on the foe<br>
 * - Status applied if any (fix score)<br>
 * - Stat alterations<br>
 *
 * Doesn't use the same attack two times in a row.
 */
public class WiseStrategy implements IStrategy {
    private final PlayerAI player;
    private ArrayList<ScoredAttack> scoredAttacks = new ArrayList<>();

    public WiseStrategy(PlayerAI player){
        this.player = player;
        initProbas();
    }

    private void initProbas(){
        ArrayList<IAttack> attacks = player.getAllyAnimal().getAttacks();
        for (IAttack attack :
                attacks) {
            ScoredAttack scoredAttack = new ScoredAttack(attack, 0, 100);
            scoredAttacks.add(scoredAttack);
        }
    }

    /**
     * Choose an attack based on a score system for every attack available.
     * @return The chosen attack.
     */
    @Override
    public IAttack chooseAttack() {
        refreshScore();

        IAttack chosenAttack = scoredAttacks.get(0).getAttack();
        int scoreMax = ScoredAttack.getMaxProba(scoredAttacks);
        for(ScoredAttack scoredAttack : scoredAttacks){
            if (scoredAttack.isEnabled() && RNG.RNGsuccess(scoreMax, scoredAttack.getProba())) {
                chosenAttack = scoredAttack.getAttack();
                // Probability for the attack to be chosen again next time is lowered.
                scoredAttack.setProba(scoredAttack.getProba() / 3);
                break;
            }
        }
        return chosenAttack;
    }

    /**
     * Recalculate the score of every attack, and order the list (greatest to lowest)
     */
    private void refreshScore() {
        for (ScoredAttack scoredAttack :
                scoredAttacks) {
            IAttack attack = scoredAttack.getAttack();
            int score = calculateScore(attack, player.getFoesAnimal());
            scoredAttack.setScore(score);
        }
        scoredAttacks.sort(Collections.reverseOrder());
    }


    @Override
    public int getStrategyLevel() {
        return 0;
    }

    private boolean canKill(IAttack attack, IAnimal target){
        return Attack.simulateAttack(player.getAllyAnimal(), target, attack) >= target.getHealth();
    }

    /**
     * Calculate the score of an attack.
     * @param attack Attack to score.
     * @param target Target of the attack.
     * @return Score of the attack.
     */
    private int calculateScore(IAttack attack, IAnimal target){
        int score = 0;
        for(IActionBehavior behavior : attack.getBehaviors()){
            score += behavior.score(target);
        }
        if(canKill(attack, target)) {
            score += 1000;
        }
        return score;
    }
}
