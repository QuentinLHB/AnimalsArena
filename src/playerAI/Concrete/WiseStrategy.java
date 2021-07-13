package playerAI.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Action.Status.Concrete.Status_Base;
import Animal.Behaviors.DefendBehavior.Concrete.Defend_Base;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;
import Util.RNG;
import playerAI.Abstract.IStrategy;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

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

    @Override
    public IAttack chooseAttack() {
        for (ScoredAttack scoredAttack :
                scoredAttacks) {
            IAttack attack = scoredAttack.getAttack();
            IAnimal target = attack.isSelfInflicting() ? player.getAllyAnimal() : player.getFoesAnimal();
            int score = calculateScore(attack, target);
            scoredAttack.setScore(score);
        }

        Collections.sort(scoredAttacks, Collections.reverseOrder());

        IAttack chosenAttack = scoredAttacks.get(0).getAttack();
        int scoreMax = ScoredAttack.getMaxProba(scoredAttacks);
        for(ScoredAttack scoredAttack : scoredAttacks){
            if(RNG.RNGsuccess(scoreMax, scoredAttack.getProba())) {
                chosenAttack = scoredAttack.getAttack();
                // Probability for the attack to be chosen again next time is lowered by half.
                scoredAttack.setProba(scoredAttack.getProba()/2);
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
        if(canKill(attack, target)) {
            score += 1000;
        }
        score += Attack.simulateDamage(player.getAllyAnimal(), target, attack);
        if(attack.getStatusInflicted() != null && doesAttackInflictNewStatus(attack, target)) score += 20;
        if(attack.getStatAlterations() != null){
            float statusScore = 1;
            for (Float amount: attack.getStatAlterations().values()) {
                statusScore *= 1-(1+amount);
            }
            score += Math.round(20*statusScore);
        }
        return score;
    }


    /**
     * Checks if the attack inflicts a new status to the foe.
     * @param attack Attack used for the simulation.
     * @param target Target of the attack.
     * @return True if is a new status, else false. /!\ If no status is inflicted by the attack, this method returns true.
     */
    private boolean doesAttackInflictNewStatus(IAttack attack, IAnimal target){
        return !Status_Base.doesStatusAlreadyExist(target, attack.getStatusInflicted());
    }
}
