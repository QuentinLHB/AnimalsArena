package Model.Action.AlterStats;

import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;
import View.BufferedText;

import java.util.Map;

public class AlterFoesStatsBehavior extends AlterAllyStatsBehavior {


    public AlterFoesStatsBehavior(Map<StatID, Float> statsToAlter) {
        super(statsToAlter);
    }

    @Override
    public void execute(IAnimal target) {
        StringBuilder effectToDisplay = new StringBuilder();
        for(Map.Entry<StatID, Float> stat: statsToAlter.entrySet()){
            target.alterStat(stat.getKey(), stat.getValue());
            effectToDisplay.append(effectDisplay(target, stat.getKey()));
        }
        BufferedText.addBufferedText(effectToDisplay.toString());
    }

    @Override
    public String getDescription() {
        String desc = super.getDescription();
        return desc.replace("user", "foe");
    }

    /**
     * Calculate a score based on the current stat alteration of the stats altered by the attack.
     * @param target Target of the attack.
     * @return Score of the attack.
     */
    @Override
    public int score(IAnimal target) {
        int score = 0;
        for (Map.Entry<StatID, Float> statToAlter :
                statsToAlter.entrySet()) {
            if(statToAlter.getValue() < 1){
                float animalsStatAlt = target.getStatAlteration(statToAlter.getKey());
                if(animalsStatAlt <= 0.5f){
                    score += calculateScore(target, 25, statToAlter);
                }
                else if(animalsStatAlt <= 0.9f){
                    score += calculateScore(target, 50, statToAlter);
                }
                else{
                    score += calculateScore(target, 100, statToAlter);
                }
            }
        }
        return score;
    }

    /**
     * Calculate the score following the formula :
     * Basis * Value of the stat alteration  * Value of the stat altered by the attack (point being to emphasis the most important stats).
     * ex : 100 * 0.2 * 1.1 = 23
     * @param target Target of the attack
     * @param basis Basis of the formula.
     * @param statToAlter Entry of a Map :
     * @return
     */
    private int calculateScore(IAnimal target, int basis, Map.Entry<StatID, Float> statToAlter){
        float attacksStatAlt = 1-statToAlter.getValue();
        float targetsStat = target.getStat(statToAlter.getKey());
        return Math.round(basis * attacksStatAlt * targetsStat);
    }
}
