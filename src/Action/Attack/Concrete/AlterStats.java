package Action.Attack.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.StatID;

import java.util.Map;

public class AlterStats extends Attack implements IAttack {

    Animal attackOwner;
    /**
     * Dictionnary :
     * StatID : Stats to alter.
     * Float : Amount by which the stat must be multiplied.
     */
    Map<StatID, Float> statsToAlter;

    public AlterStats(Animal attackOwner, String name, Map<StatID, Float> statsToAlter) {
        super(attackOwner, name, 0, new InflictNoStatus());
        this.statsToAlter = statsToAlter;
    }

    public AlterStats(String name, Map<StatID, Float> statsToAlter) {
        super(name, 0, new InflictNoStatus());
        this.statsToAlter = statsToAlter;
    }

    @Override
    public void performAttack(IAnimal target, int damage) {
        for(StatID statToAlter: statsToAlter.keySet()){
            target.alterStat(statToAlter, statsToAlter.get(statToAlter));
        }
    }


}
