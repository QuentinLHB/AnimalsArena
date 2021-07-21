package Action.Attack.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.InflictStatus.Abstract.IInflictStatus;
import Action.InflictStatus.Concrete.InflictNoStatus;
import Action.InflictStatus.Concrete.InflictStatus;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;
import Util.RNG;

import java.util.Map;
import java.util.Random;

public class RandomPowerAttack extends Attack implements IAttack {

    private int minDmg;
    private int maxDmg;

    /**
     * Full constructor of an attack with random power.
     * @param attackOwner Attack owner
     * @param name name of the attack.
     * @param accuracy Accuracy of the attack.
     * @param minDmg Minimum damage possible.
     * @param maxDmg Maximum damage possible.
     * @param inflictStatus Status inflicted.
     * @param statsToAlter Stats altered and amount.
     * @param selfInflicting Is the attack self-inflicting ?
     * @param description optional description.
     */
    public RandomPowerAttack(IAnimal attackOwner, String name, int minDmg, int maxDmg, float accuracy,
                             IInflictStatus inflictStatus, Map<StatID, Float> statsToAlter, boolean selfInflicting,
                             String description){
        super(attackOwner, name, (maxDmg+minDmg)/2, accuracy, inflictStatus, statsToAlter, selfInflicting, description);
        if(maxDmg < minDmg){
            int temp = maxDmg;
            maxDmg = minDmg;
            minDmg = temp;
        }
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
    }

    public RandomPowerAttack(IAnimal attackOwner, String name, int minDmg, int maxDmg, float accuracy,
                             IInflictStatus inflictStatus, Map<StatID, Float> statsToAlter, boolean selfInflicting){
        this(attackOwner, name, minDmg, maxDmg, accuracy, inflictStatus, statsToAlter, selfInflicting, null);
    }

    public RandomPowerAttack(IAnimal attackOwner, String name, int minDmg, int maxDmg, float accuracy,
                             IInflictStatus inflictStatus, boolean selfInflicting){
        this(attackOwner, name, minDmg, maxDmg, accuracy, inflictStatus, null, selfInflicting, null);
    }

    public RandomPowerAttack(IAnimal attackOwner, String name, int minDmg, int maxDmg, float accuracy){
        this(attackOwner, name, minDmg, maxDmg, accuracy, new InflictNoStatus(), null, false, null);
    }


    @Override
    public void performAttack(IAnimal target) {
        super.damageBase = RNG.GenerateNumber(minDmg, maxDmg);
        super.performAttack(target);
    }

    /**
     *
     * @return Average damage of the attack.
     */
    @Override
    public int getDamageBase() {
        return (maxDmg+minDmg)/2;
    }

}
