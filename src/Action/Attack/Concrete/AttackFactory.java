package Action.Attack.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.InflictStatus.Concrete.InflictStatus;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.StatID;
import Util.RNG;

import java.util.*;

public class AttackFactory {

    private AttackFactory(){}

    public static IAttack createAttack(Animal animal, AttackEnum attackName){
        IAttack attack;

        switch (attackName){
            case DEFEND -> attack = new Defend();
            case BITE -> attack = new Attack(animal, "Bite", 15, 1);
            case FIRE_BITE -> attack = new Attack(animal, "Fire Bite", 20, 1);
            case POISON_BITE -> attack = new Attack(animal, "Poison Bite", 10, 1, new InflictStatus(StatusID.POISON, 2));
            case DEATH_BITE -> attack = new HealingMove(animal, "Death Bite", 15, 1);
            case HYPNOSIS -> attack = new Attack(animal, "Hypnosis", 0, 0.6f, new InflictStatus(StatusID.SLEEP));
            case GROWL -> attack = new Attack(animal, "Growl", 0, 1, new InflictStatus(StatusID.FEAR, 2));
            case PEWK -> attack = new Attack(animal, "Pewk", 5, 1, new InflictStatus(StatusID.POISON, 3));
            case HEALING_POWER -> attack = new HealingMove(animal, "Healing Power", 0.2f, 1);
            case TORNADO -> attack = new Attack(animal, "Tornado", 10, 0.8f, new InflictStatus(StatusID.FEAR));
            case FLAMETHROWER -> attack = new Attack(animal, "Flamethrower", 20, 0.8f);
            case SPIT -> attack = new Attack(animal, "Spit", 10, 1, new InflictStatus(StatusID.FEAR, 3));
            case TSUNAMI -> attack = new Attack(animal, "Tsunami", 15, 1);
            case RAGE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 1.25f);
                stats.put(StatID.DEFENSE, 0.8f);
                attack =new Attack(animal, "Rage", 0, 1, stats, true);
            }
            case FURY -> {
                attack = new Attack(animal, "Fury", 50, 0.9f, new InflictStatus(StatusID.SLEEP, 2, true));
            }
            case PURR -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 0.9f);
                stats.put(StatID.DEFENSE, 0.9f);
                attack =new Attack(animal, "Purr", 0, 0.9f, stats, false);
            }
            case STOMP -> attack = new Attack(animal, "Stomp", 35, 0.5f, new InflictStatus(StatusID.FEAR, 2));
            case GIFT_OF_LIFE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.MAX_HEALTH, 1.5f);
                attack =new Attack(animal, "Gift of life", 0, 0.8f, stats, true);
            }

            //add here
            default -> attack = new Attack(animal, "Cry", 0, 1f);
        }
        return attack;

    }

    public static ArrayList<IAttack> getAllAttacks(){
        ArrayList<IAttack> allAttacks = new ArrayList<>();
        for (AttackEnum attack:AttackEnum.values()) {
            allAttacks.add(createAttack(null, attack));
        }
        return allAttacks;

    }
}
