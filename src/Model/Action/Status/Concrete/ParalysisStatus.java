package Model.Action.Status.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Util.RNG;
import View.BufferedText;

import java.util.ArrayList;

public class ParalysisStatus extends Status_Base implements IStatus {
    private static final int DEFAULT_DURATION = 3;
    private ArrayList<IAttack> disabledAttacks = new ArrayList<>();

    public ParalysisStatus(IAnimal animal) {
        this(animal, DEFAULT_DURATION);
    }

    public ParalysisStatus(IAnimal animal, int duration){
        super(animal, duration);
        super.printEffect("paralyzed");
        disableAttacks();
    }

    /**
     * Disable half of the attacks if more than 2 attacks are possessed.
     */
    private void disableAttacks(){

        if(animal.getAttacks().size() <= 2) {
            disappear();
            return;
        }
        int amountOfAttacksToDisable = animal.getAttacks().size()/2;

        for (int i = 0; i <amountOfAttacksToDisable; i++) {
            ArrayList<IAttack> attacks = animal.getAttacks();
            IAttack attackToDisable;
            do{
               attackToDisable = attacks.get(RNG.GenerateNumber(0, attacks.size()-1));
            }while (disabledAttacks.contains(attackToDisable));

            disabledAttacks.add(attackToDisable);
            attackToDisable.enable(false);
            BufferedText.addBufferedText(String.format("%s was disabled.%n", attackToDisable.getAttackName()));
        }
    }

    @Override
    public StatusID getStatusID() {
        return StatusID.PARALYSIS;
    }

    @Override
    public String getStatusName() {
        return "paralysis";
    }

    @Override
    public void consumeEffect() {
        if(turnsLeft > 0){
            turnsLeft--;
        }
        else disappear();
    }

    @Override
    public void disappear() {
        super.disappear(this);
        BufferedText.addBufferedText(String.format(
                "%s is no longer paralysed, all attacks are usable again.%n", animal.getName()));
        for (IAttack attack :
                disabledAttacks) {
            attack.enable(true);
        }
    }

    @Override
    public int getDefaultDuration() {
        return DEFAULT_DURATION;
    }


}
