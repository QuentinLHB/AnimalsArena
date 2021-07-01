package Damage;

import Animal.IAnimal;
import Animal.Stat;

public class SleepStatus extends Status_Base implements IStatus{

    private final int NB_OF_TURNS = 2;

    public SleepStatus(IAnimal animal){
        super(animal);
        super.turnsLeft = NB_OF_TURNS;
        super.nbOfTurns = NB_OF_TURNS;
        System.out.println(String.format("%s falls asleep. zZzZ", animal.getName()));
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.SLEEP;
    }

    @Override
    public void consumeEffect() {
        animal.allowActions(false);
    }

    @Override
    public int getTurnsLeft() {
        return turnsLeft;
    }

    @Override
    public void disappear() {
        super.disappear(this);
        System.out.println(String.format("%n wakes up !", animal.getName()));
    }

    @Override
    public int getTurns() {
        return 0;
    }
}
