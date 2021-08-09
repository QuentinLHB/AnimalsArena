package Model.Action.Attack.Concrete;

import Model.Action.AlterStats.AlterAllyStatsBehavior;
import Model.Action.AlterStats.AlterFoesStatsBehavior;
import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Defend.SimpleDefendBehavior;
import Model.Action.DoDamage.DoNoDamageBehavior;
import Model.Action.DoDamage.DoRandomDamage;
import Model.Action.DoDamage.SimpleDoDamageBehavior;
import Model.Action.Heal.HealFixAmountBehavior;
import Model.Action.Heal.HealPercentageBehavior;
import Model.Action.InflictStatus.InflictStatusBehavior;
import Model.Action.InflictStatus.SelfInflictStatusBehavior;
import Model.Action.Status.Concrete.StatusID;
import Model.Animal.Creation.Concrete.Animal;
import Model.Animal.Creation.Concrete.StatID;

import java.util.*;

/**
 * Attack Factory used to create pre-configured attacks.
 */
public class AttackFactory {

    private AttackFactory(){}

    /**
     * Create an attack using pre-existing attacks, matching the AttackEnum enumerator class.
     * The attack is directly added to the animal.
     * @param animal Model.Animal on which the attack must be added.
     * @param attackName Attack to add, from the AttackEnum enumerator class.
     */
    public static void addAttackToAnimal(Animal animal, AttackEnum attackName){
        switch (attackName){
            case DEFEND -> new Attack(animal, attackName.toString(), 1f, new DoNoDamageBehavior(), new SimpleDefendBehavior());

            case BITE -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(15));

            case FIRE_BITE -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(20));

            case POISON_BITE -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(15),
                        new InflictStatusBehavior(StatusID.POISON));

            case DEATH_BITE -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(15),
                        new HealFixAmountBehavior(15/2));

            case SHIELD_ATTACK -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(10),
                    new SimpleDefendBehavior());

            case HYPNOSIS -> new Attack(animal, attackName.toString(), 0.6f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.SLEEP));

            case GROWL -> new Attack(animal, attackName.toString(), 1f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.FEAR, 2));

            case PEWK -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(5),
                    new InflictStatusBehavior(StatusID.POISON, 3));

            case HEALING_POWER -> new Attack(animal, attackName.toString(), 1f, new DoNoDamageBehavior(),
                    new HealPercentageBehavior(1.2f));

            case TORNADO -> new Attack(animal, attackName.toString(), 0.8f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.FEAR));

            case FLAMETHROWER -> new Attack(animal, attackName.toString(), 0.8f, new SimpleDoDamageBehavior(20));

            case SPIT -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.FEAR, 3));

            case TSUNAMI -> new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(15));

            case RAGE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 1.25f);
                stats.put(StatID.DEFENSE, 0.9f);
                new Attack(animal, attackName.toString(), 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }
            case FURY -> {
                new Attack(animal, attackName.toString(), 0.7f, new SimpleDoDamageBehavior(30),
                        new SelfInflictStatusBehavior(StatusID.PARALYSIS, 3));
            }

            case HEADBUTT -> new Attack(animal, attackName.toString(), 1f, new DoRandomDamage(1, 30));

            case PURR -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 0.9f);
                stats.put(StatID.DEFENSE, 0.9f);
                new Attack(animal, attackName.toString(),1f, new DoNoDamageBehavior(),
                        new AlterFoesStatsBehavior(stats));
            }

            case STOMP -> new Attack(animal, attackName.toString(), 0.5f, new SimpleDoDamageBehavior(35),
                    new InflictStatusBehavior(StatusID.FEAR, 2));

            case GIFT_OF_LIFE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.MAX_HEALTH, 1.25f);
                new Attack(animal, attackName.toString(), 0.8f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case THUNDER -> new Attack(animal, attackName.toString(), 0.8f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 2));

            case FRY ->  new Attack(animal, attackName.toString(), 1f, new SimpleDoDamageBehavior(5),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 5));

            case BONFIRE -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.ATTACK, 1.2f);
                new Attack(animal, attackName.toString(), 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case ICESHIELD -> {
                Map<StatID, Float> stats = new HashMap<>();
                stats.put(StatID.DEFENSE, 1.2f);
                new Attack(animal, attackName.toString(), 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case PERMASTINK -> new Attack(animal, attackName.toString(), 0.8f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.POISON, 7));

            case INTIMIDATION -> new Attack(animal, attackName.toString(), 1f, new DoNoDamageBehavior(),
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

    public static String getAttackDescription(AttackEnum attack){
        Animal fakeAnimal = new Animal(0, 0, 0, 0);
        addAttackToAnimal(fakeAnimal, attack);
        return fakeAnimal.chooseAttack(0).getDescription();
    }
}
