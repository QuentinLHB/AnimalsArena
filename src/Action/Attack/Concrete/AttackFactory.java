package Action.Attack.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.InflictStatus.Concrete.InflictStatus;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.StatID;

import java.util.*;

/**
 * Attack Factory used to create pre-configured attacks.
 */
public class AttackFactory {

    private AttackFactory(){}

    /**
     * Create an attack using pre-existing attacks, matching the AttackEnum enumerator class.
     * The attack is directly added to the animal.
     * @param animal Animal on which the attack must be added.
     * @param attackName Attack to add, from the AttackEnum enumerator class.
     */
    public static void addAttackToAnimal(Animal animal, AttackEnum attackName){
        switch (attackName){
            case DEFEND -> new Defend(animal);
            case BITE -> new Attack(animal, "Bite", 15, 1);
            case FIRE_BITE -> new Attack(animal, "Fire Bite", 20, 1);
            case POISON_BITE -> new Attack(animal, "Poison Bite", 10, 1, new InflictStatus(StatusID.POISON, 2));
            case DEATH_BITE -> new HealingMove(animal, "Death Bite", 15, 1);
            case HYPNOSIS -> new Attack(animal, "Hypnosis", 0, 0.6f, new InflictStatus(StatusID.SLEEP));
            case GROWL -> new Attack(animal, "Growl", 0, 1, new InflictStatus(StatusID.FEAR, 2));
            case PEWK -> new Attack(animal, "Pewk", 5, 1, new InflictStatus(StatusID.POISON, 3));
            case HEALING_POWER -> new HealingMove(animal, "Healing Power", 0.2f, 1);
            case TORNADO -> new Attack(animal, "Tornado", 10, 0.8f, new InflictStatus(StatusID.FEAR));
            case FLAMETHROWER -> new Attack(animal, "Flamethrower", 20, 0.8f);
            case SPIT -> new Attack(animal, "Spit", 10, 1, new InflictStatus(StatusID.FEAR, 3));
            case TSUNAMI -> new Attack(animal, "Tsunami", 15, 1);
            case RAGE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 1.25f);
                stats.put(StatID.DEFENSE, 0.8f);
                new Attack(animal, "Rage", 0, 1, stats, true);
            }
            case FURY -> {
                new Attack(animal, "Fury", 50, 0.9f, new InflictStatus(StatusID.PARALYSIS, 3, true));
            }

            case HEADBUTT -> new RandomPowerAttack(animal, "Headbutt", 5, 30, 1f);

            case PURR -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 0.9f);
                stats.put(StatID.DEFENSE, 0.9f);
                new Attack(animal, "Purr", 0, 0.9f, stats, false);
            }
            case STOMP -> new Attack(animal, "Stomp", 35, 0.5f, new InflictStatus(StatusID.FEAR, 2));
            case GIFT_OF_LIFE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.MAX_HEALTH, 1.25f);
                new Attack(animal, "Gift of life", 0, 0.8f, stats, true);
            }

            case THUNDER -> new Attack(animal, "Thunder", 10, 0.8f, new InflictStatus(StatusID.PARALYSIS, 2));
            case FRY ->  new Attack(animal, "Fry", 5, 1f, new InflictStatus(StatusID.PARALYSIS, 5));
            case BONFIRE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 1.2f);
                new Attack(animal, "Bonfire", 0, 1, stats, true);
            }

            case ICESHIELD -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.DEFENSE, 1.2f);
                new Attack(animal, "Iceshield", 0, 1, stats, true);
            }

            case PERMASTINK -> new Attack(animal, "Perma-Stink", 0, 0.8f, new InflictStatus(StatusID.POISON, 7));

            case INTIMIDATION -> new Attack(animal, "Intimidation", 0, 1f, new InflictStatus(StatusID.PARALYSIS, 5));
            //add here
            default -> new Attack(animal, "Cry", 0, 1f);
        }

    }

    /**
     * @return List of all the attacks that can be created by the factory.
     */
    public static ArrayList<IAttack> getAllAttacks(){
        Animal fakeAnimal = new Animal(0, 0, 0, 0);
        for (AttackEnum attack:AttackEnum.values()) {
            if(attack.equals(AttackEnum.ICESHIELD)){
                int a= 1;
            }
            addAttackToAnimal(fakeAnimal, attack);
        }
        return fakeAnimal.getAttacks();
    }
}
