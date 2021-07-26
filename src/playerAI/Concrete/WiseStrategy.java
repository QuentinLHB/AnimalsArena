package playerAI.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Action.IActionBehavior;
import Action.InflictStatus.IInflictStatusBehavior;
import Action.Status.Concrete.Status_Base;
import Animal.Creation.Abstract.IAnimal;
import Util.RNG;
import playerAI.Abstract.IStrategy;

import java.lang.reflect.Array;
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

    @Override
    public IAttack chooseAttack() {
        refreshAttackList();
        for (ScoredAttack scoredAttack :
                scoredAttacks) {
            IAttack attack = scoredAttack.getAttack();

            int score = calculateScore(attack, player.getFoesAnimal());

            scoredAttack.setScore(score);
        }

        Collections.sort(scoredAttacks, Collections.reverseOrder());

        IAttack chosenAttack = scoredAttacks.get(0).getAttack();
        int scoreMax = ScoredAttack.getMaxProba(scoredAttacks);
        for(ScoredAttack scoredAttack : scoredAttacks){
            if (RNG.RNGsuccess(scoreMax, scoredAttack.getProba())) {
                chosenAttack = scoredAttack.getAttack();
                // Probability for the attack to be chosen again next time is lowered.
                scoredAttack.setProba(scoredAttack.getProba() / 3);
                break;
            }
        }
        return chosenAttack;
    }



    @Override
    public int getStrategyLevel() {
        return 0;
    }

    private boolean canKill(IAttack attack, IAnimal target){
        return Attack.simulateDamage(player.getAllyAnimal(), target, attack) >= target.getHealth();
    }

    private int calculateScore(IAttack attack, IAnimal target){

        int score = 0;
        for(IActionBehavior behavior : attack.getBehaviors()){
            score += behavior.score(target);
        }

        if(canKill(attack, target)) {
            score += 1000;
        }

        System.out.printf("Score %s : %d%n", attack.getAttackName(), score);

        return score;
    }
}
