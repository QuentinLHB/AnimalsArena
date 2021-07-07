package Action.Status.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;

public class FearStatus extends Status_Base implements IStatus {

    /**
     * Effect applied to the attack stat of the frightened foe. Default is 0.5.
     */
    public float onAttackEffect;
    public static final float DEFAULT_ATTACKEFFECT  = 0.5f;
    /**
     * Effect applied to the defense stat of the frightened foe. Default is 0.5.
     */
    public float onDefenseEffect;
    public static final float DEFAULT_DEFENSEEFFECT  = 0.5f;
    private static final int DEFAULT_DURATION = 3;

    public FearStatus(IAnimal animal) {
        this(animal, DEFAULT_DURATION);
    }

    public FearStatus(IAnimal animal, int duration){
        this(animal, duration, DEFAULT_ATTACKEFFECT, DEFAULT_DEFENSEEFFECT);
    }

    public FearStatus(IAnimal animal, int duration, float onAttackEffect, float onDefenseEffect){
        super(animal, duration);
        super.duration = duration;
        super.turnsLeft = duration;
        this.onAttackEffect = onAttackEffect;
        this.onDefenseEffect = onDefenseEffect;
        printEffect();
        animal.alterStat(StatID.ATTACK, onAttackEffect);
        animal.alterStat(StatID.DEFENSE, onDefenseEffect);
        animal.canDefend(false);
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.FEAR;
    }

    @Override
    public String getStatusName() {
        return StatusID.FEAR.lowerCaseName();
    }

    @Override
    public void consumeEffect() {
        if(turnsLeft > 0){
            turnsLeft--;
        }
        else disappear();
    }

    @Override
    public int getTurnsLeft() {
        return turnsLeft;
    }

    @Override
    public void disappear() {
        super.disappear(this);
        animal.alterStat(StatID.ATTACK, 1);
        animal.alterStat(StatID.DEFENSE, 1);
        animal.canDefend();
        System.out.printf("%s is no longer frightened%n", animal.getName());
    }

    @Override
    public int getDefaultDuration() {
        return DEFAULT_DURATION;
    }

    private void printEffect(){
        System.out.printf("%s was frightened%n", animal.getName());
    }
}
