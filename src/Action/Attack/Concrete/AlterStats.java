package Action.Attack.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.StatID;

import java.util.Map;

public class AlterStats extends Attack implements IAttack {

    /**
     * Dictionnary :
     * StatID : Stats to alter.
     * Float : Amount by which the stat must be multiplied.
     */
    Map<StatID, Float> statsToAlter;

    /**
     * Full constructor of a stat-altering move. (ex: A move that lowers attack and defense stats permanently)
     * @param attackOwner Owner of the attack.
     * @param name Name of the attack.
     * @param accuracy Accuracy of the attack (float between 0 and 1).
     * @param statsToAlter Dictionnary (Map) : StatID, Float,
     */
    public AlterStats(Animal attackOwner, String name, float accuracy, Map<StatID, Float> statsToAlter) {
        super(attackOwner, name, 0, accuracy, new InflictNoStatus());
        this.statsToAlter = statsToAlter;
    }

    public AlterStats(String name, float accuracy, Map<StatID, Float> statsToAlter) {
        super(name, 0, accuracy, new InflictNoStatus());
        this.statsToAlter = statsToAlter;
    }

    public AlterStats(String name, Map<StatID, Float> statsToAlter){
        this(name, 1f, statsToAlter);
    }

    @Override
    public void performAttack(IAnimal target, float attackStat) {
        if(accuracyTest()){
            for(StatID statToAlter: statsToAlter.keySet()){
                target.alterStat(statToAlter, statsToAlter.get(statToAlter));
            }
        }

    }


}
