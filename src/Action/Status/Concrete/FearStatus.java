package Action.Status.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Abstract.IAnimal;
import Animal.Concrete.StatID;

public class FearStatus extends Status_Base implements IStatus {

    /**
     * Effect applied to the attack stat of the frightened foe. Default is 0.5.
     */
    public float onAttackEffect = 0.5f;
    /**
     * Effect applied to the defense stat of the frightened foe. Default is 0.5.
     */
    public float onDefenseEffect = 0.5f;

    public FearStatus(IAnimal animal) {
        super(animal);
        animal.alterStat(StatID.ATTACK, onAttackEffect);
        animal.alterStat(StatID.DEFENSE, onDefenseEffect);
    }

    public FearStatus(IAnimal animal, int duration){
        this(animal);
        super.duration = duration;
    }

    public FearStatus(IAnimal animal, int duration, float onAttackEffect, float onDefenseEffect){ //Todo permettre l'accès via une factory
        this(animal, duration);
        this.onAttackEffect = onAttackEffect;
        this.onDefenseEffect = onDefenseEffect;
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
            duration--;
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
        System.out.printf("%s is no longer frightened%n", animal.getName());
    }

    @Override
    public int getTurns() {
        return 0;
    }
}
