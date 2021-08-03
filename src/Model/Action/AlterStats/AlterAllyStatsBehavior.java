package Model.Action.AlterStats;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;

import java.util.Map;

public class AlterAllyStatsBehavior implements IAlterStatsBehavior {
    protected IAttack attack;
    protected Map<StatID, Float> statsToAlter;

    public AlterAllyStatsBehavior(Map<StatID, Float> statsToAlter){
        this.statsToAlter = statsToAlter;
    }

    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public void execute(IAnimal target) {
        target = attack.getAttackOwner();
        for(StatID stat: statsToAlter.keySet()){
            target.alterStat(stat, statsToAlter.get(stat));
        }
    }

    @Override
    public int score(IAnimal target) {
        int score = 0;
        for (Map.Entry<StatID, Float> statToAlter :
                statsToAlter.entrySet()) {
            if(statToAlter.getValue() > 1){
                float animalsStatAlt = attack.getAttackOwner().getStatAlteration(statToAlter.getKey());
                if(animalsStatAlt <= 1.25){
                    score += calculateScore(100, statToAlter);
                }
                else if(animalsStatAlt <= 1.5){
                    score += calculateScore(50, statToAlter);
                }
                else if(animalsStatAlt < 2){
                    score += calculateScore(25, statToAlter);
                }
            }
            else{
                score -= 100 * (1-statToAlter.getValue());
            }
        }
        return score;
    }

    private int calculateScore(int base, Map.Entry<StatID, Float> statToAlter){
        float animalsStat = attack.getAttackOwner().getStat(statToAlter.getKey());
        if(statToAlter.getKey().equals(StatID.MAX_HEALTH))
            animalsStat /= 100;
        return Math.round(base * (statToAlter.getValue()-1) * animalsStat);
    }

    @Override
    public String getDescription() {
        String description = "";
        for (Map.Entry<StatID, Float> stat :
                statsToAlter.entrySet()) {
            String effect;
            int amount;
            if(stat.getValue() >= 1){
                effect = "Raises";
                amount = Math.round((stat.getValue()-1)*100);
            }
            else{
                effect = "Lower";
                amount = Math.round((1-stat.getValue())*100);
            }
            description += String.format("%s the user's %s stat by %d%s | ",
                    effect, stat.getKey().name(), amount, "%");
        }
        return description.substring(0, description.length()-3);
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
