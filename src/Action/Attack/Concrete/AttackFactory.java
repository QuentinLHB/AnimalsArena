package Action.Attack.Concrete;

import Action.AlterStats.AlterAllyStatsBehavior;
import Action.AlterStats.AlterFoesStatsBehavior;
import Action.AlterStats.AlterNoStatBehavior;
import Action.AlterStats.IAlterStatsBehavior;
import Action.Attack.Abstract.IAttack;
import Action.DoDamage.DoNoDamageBehavior;
import Action.DoDamage.DoRandomDamage;
import Action.DoDamage.IDoDamageBehavior;
import Action.DoDamage.SimpleDoDamageBehavior;
import Action.Heal.HealFixAmountBehavior;
import Action.Heal.HealPercentageBehavior;
import Action.Heal.IHealBehavior;
import Action.Heal.NoHealBehavior;
import Action.InflictStatus.IInflictStatusBehavior;
import Action.InflictStatus.InflictNoStatusBehavior;
import Action.InflictStatus.InflictStatusBehavior;
import Action.InflictStatus.SelfInflictStatusBehavior;
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

            case BITE -> new Attack(animal, "Bite", 1f, new SimpleDoDamageBehavior(15));

            case FIRE_BITE -> new Attack(animal, "Fire Bite", 1f, new SimpleDoDamageBehavior(20));

            case POISON_BITE -> new Attack(animal, "Poison Bite", 1f, new SimpleDoDamageBehavior(15),
                        new InflictStatusBehavior(StatusID.POISON));

            case DEATH_BITE -> new Attack(animal, "Death Bite", 1f, new SimpleDoDamageBehavior(15),
                        new HealFixAmountBehavior(15/2));

            case HYPNOSIS -> new Attack(animal, "Hypnosis", 0.6f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.SLEEP));

            case GROWL -> new Attack(animal, "Growl", 1f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.FEAR, 2));

            case PEWK -> new Attack(animal, "Pewk", 1f, new SimpleDoDamageBehavior(5),
                    new InflictStatusBehavior(StatusID.POISON, 3));

            case HEALING_POWER -> new Attack(animal, "Healing Power", 1f, new DoNoDamageBehavior(),
                    new HealPercentageBehavior(1.2f));

            case TORNADO -> new Attack(animal, "Tornado", 0.8f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.FEAR));

            case FLAMETHROWER -> new Attack(animal, "Flamethrower", 0.8f, new SimpleDoDamageBehavior(20));

            case SPIT -> new Attack(animal, "Spit", 1f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.FEAR, 3));

            case TSUNAMI -> new Attack(animal, "Tsunami", 1f, new SimpleDoDamageBehavior(15));

            case RAGE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 1.25f);
                stats.put(StatID.DEFENSE, 0.9f);
                new Attack(animal, "Rage", 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }
            case FURY -> {
                new Attack(animal, "Fury", 0.7f, new SimpleDoDamageBehavior(30),
                        new SelfInflictStatusBehavior(StatusID.PARALYSIS, 3));
            }

            case HEADBUTT -> new Attack(animal, "Headbutt", 1f, new DoRandomDamage(1, 30));

            case PURR -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 0.9f);
                stats.put(StatID.DEFENSE, 0.9f);
                new Attack(animal, "Purr",1f, new DoNoDamageBehavior(),
                        new AlterFoesStatsBehavior(stats));
            }

            case STOMP -> new Attack(animal, "Stomp", 0.5f, new SimpleDoDamageBehavior(35),
                    new InflictStatusBehavior(StatusID.FEAR, 2));

            case GIFT_OF_LIFE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.MAX_HEALTH, 1.25f);
                new Attack(animal, "Gift of life", 0.8f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case THUNDER -> new Attack(animal, "Thunder", 0.8f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 2));

            case FRY ->  new Attack(animal, "Fry", 1f, new SimpleDoDamageBehavior(5),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 5));

            case BONFIRE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 1.2f);
                new Attack(animal, "Bonfire", 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case ICESHIELD -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.DEFENSE, 1.2f);
                new Attack(animal, "Iceshield", 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case PERMASTINK -> new Attack(animal, "Perma-Stink", 0.8f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.POISON, 7));

            case INTIMIDATION -> new Attack(animal, "Intimidation", 1f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 5));

            //add here

            default -> new Attack(animal, "Cry",  1f, new DoNoDamageBehavior());
        }

    }

    /**
     * @return List of all the attacks that can be created by the factory.
     */
    public static ArrayList<IAttack> getAllAttacks(){
        Animal fakeAnimal = new Animal(0, 0, 0, 0);
        for (AttackEnum attack:AttackEnum.values()) {
            addAttackToAnimal(fakeAnimal, attack);
        }
        return fakeAnimal.getAttacks();
    }
}
