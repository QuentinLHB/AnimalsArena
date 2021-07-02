package Action.Attack.Concrete;

import Action.InflictStatus.Concrete.InflictNoStatus;
import Action.InflictStatus.Concrete.InflictPoison;
import Action.InflictStatus.Concrete.InflictSleep;

public class AttackFactory {

    private AttackFactory(){}

    public static Attack createAttack(AttackEnum attackName){
        Attack attack;

        switch (attackName){
            case BITE -> attack = new Attack("Bite", 15, new InflictNoStatus());
            case FIRE_BITE -> attack = new Attack("Fire Bite", 20, new InflictNoStatus());
            case POISON_BITE -> attack = new Attack("Poison Bite", 10, new InflictPoison());
            case HYPNOSIS -> attack = new Attack("Hypnosis", 0, new InflictSleep());
            default -> attack = new Attack ("Cry", 0, new InflictNoStatus());
        }

        return attack;

    }
}
