package Model.playerAI.Concrete;

import Model.Action.Attack.Abstract.IAttack;

import java.util.ArrayList;

public class ScoredAttack implements Comparable {
    public IAttack getAttack() {
        return attack;
    }

    private IAttack attack;
    private int score;

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getProba() {
        return proba;
    }

    public void setProba(int proba) {
        this.proba = proba;
    }

    private int proba;

    public ScoredAttack(IAttack attack, int score, int proba){
        this.attack = attack;
        this.score = score;
        this.proba = proba;

    }

    @Override
    public int compareTo(Object o) {
        ScoredAttack scoredAttack = (ScoredAttack)(o);
        return this.getScore().compareTo(scoredAttack.getScore());
    }

    public static int getMaxProba(ArrayList<ScoredAttack> scoredAttacks){
        int max = 0;
        for (ScoredAttack scoredAttack:
             scoredAttacks) {
            if(scoredAttack.getScore() > 0){
                max = scoredAttack.getScore();
            }
        }
        return max;
    }

    public boolean isEnabled(){
        return attack.isEnabled();
    }

    public static void printAllProbas(ArrayList<ScoredAttack> scoredAttacks){
//        for (ScoredAttack scoredAttack :
//                scoredAttacks) {
//            System.out.printf("Proba %s : %d%n", scoredAttack.getAttack().getAttackName(), scoredAttack.getProba());
//            System.out.printf("Score %s : %d%n", scoredAttack.getAttack().getAttackName(), scoredAttack.getScore());
//        }
//        System.out.println("------");
    }
}
