package Model.playerAI.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.Attack;
import Model.Action.IActionBehavior;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Util.RNG;
import Model.playerAI.Abstract.IStrategy;

import java.util.*;

/**
 * Choose an attack based on a score calculated by taking into account :
 * - Damages dealt on the foe
 * - Status applied if any (fix score)
 * - Stat alterations
 *
 * Doesn't use the same attack two times in a row.
 */
public class WiseStrategy implements IStrategy {
    private final PlayerAI player;
//    private ;
    private ArrayList<ScoredAttack> scoredAttacks = new ArrayList<>();

//    private IAttack lastAttack;

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

    private void refreshAttackList(){
        ArrayList<IAttack> attacks = player.getAllyAnimal().getAttacks();

        if(attacks.size() < scoredAttacks.size()){
            ArrayList<ScoredAttack> attacksToRemove = new ArrayList<>();
            for (int i = 0; i < scoredAttacks.size(); i++) {
                boolean found = false;
                ScoredAttack scoredAttack = scoredAttacks.get(i);
                for (IAttack attack : attacks) {
                    if(scoredAttack.getAttack().equals(attack)) {
                        found = true;
                        break;
                    }
                }
                if(!found){
                    attacksToRemove.add(scoredAttack);
                }
            }
            for(ScoredAttack attackToRemove : attacksToRemove){
                scoredAttacks.remove(attackToRemove);
            }
        }

        if(attacks.size() > scoredAttacks.size()){
            for (IAttack attack : attacks) {
                boolean found = false;
                for (ScoredAttack scoredAttack : scoredAttacks) {
                    if(attack.equals(scoredAttack.getAttack())) {
                        found = true;
                        break;
                }
            }
                if(!found){
                    scoredAttacks.add(new ScoredAttack(attack, 0, 100));
                }
            }
        }

    }

    /**
     * Choose an attack based on a score system for every attack available.
     * @return The chosen attack.
     */
    @Override
    public IAttack chooseAttack() {
//        refreshAttackList();
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
        Collections.sort(scoredAttacks, Collections.reverseOrder());
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
