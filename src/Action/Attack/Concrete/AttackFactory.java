package Action.Attack.Concrete;

import Action.InflictStatus.Concrete.InflictStatus;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Concrete.StatID;

import java.util.*;

public class AttackFactory {

    private AttackFactory(){}

    public static Attack createAttack(AttackEnum attackName){
        Attack attack;

        switch (attackName){
            case BITE -> attack = new Attack("Bite", 15);
            case FIRE_BITE -> attack = new Attack("Fire Bite", 20);
            case POISON_BITE -> attack = new Attack("Poison Bite", 10, new InflictStatus(StatusID.POISON));
            case HYPNOSIS -> attack = new Attack("Hypnosis", 0, new InflictStatus(StatusID.SLEEP));
            case GROWL -> attack = new Attack("Purr", 0, new InflictStatus(StatusID.FEAR));
            case PEWK -> attack = new Attack("Pewk", 5, new InflictStatus(StatusID.POISON));
            case TORNADO -> attack = new Attack("Tornado", 10, new InflictStatus(StatusID.FEAR));
            case FLAMETHROWER -> attack = new Attack("Flamethrower", 20);
            case SPIT -> attack = new Attack("Spit", 10, new InflictStatus(StatusID.FEAR));
            case TSUNAMI -> attack = new Attack("Tsunami", 15);
//            case RAGE -> attack =new AlterStats("Rage", );
//            case PURR -> ;
            //add here
            default -> attack = new Attack ("Cry", 0);
        }
        return attack;

    }



    public static ArrayList<Attack> getAllAttacks(){
        ArrayList<Attack> allAttacks = new ArrayList<>();
        for (AttackEnum attack:AttackEnum.values()) {
            allAttacks.add(createAttack(attack));
        }
        return allAttacks;

    }
}
