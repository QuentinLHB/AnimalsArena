package Model.Action.Attack.Concrete;

import Model.Action.AlterStats.AlterAllyStatsBehavior;
import Model.Action.AlterStats.AlterFoesStatsBehavior;
import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Defend.SimpleDefendBehavior;
import Model.Action.DoDamage.DoNoDamageBehavior;
import Model.Action.DoDamage.DoRandomDamage;
import Model.Action.DoDamage.SimpleDoDamageBehavior;
import Model.Action.Heal.HealPartOfDmgDealt;
import Model.Action.Heal.HealPercentageBehavior;
import Model.Action.InflictStatus.InflictStatusBehavior;
import Model.Action.InflictStatus.SelfInflictStatusBehavior;
import Model.Action.Status.Concrete.StatusID;
import Model.Animal.Creation.Concrete.Animal;
import Model.Animal.Creation.Concrete.ElementType;
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
    public static Attack addAttackToAnimal(Animal animal, AttackEnum attackName){
        Attack attack;
        switch (attackName){
            case DEFEND -> attack = new Attack(animal, attackName, 1f, new DoNoDamageBehavior(), new SimpleDefendBehavior());

            case BITE -> attack =new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(15));

            case FIRE_BITE -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(20));

            case POISON_BITE -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(15),
                        new InflictStatusBehavior(StatusID.POISON));

            case DEATH_BITE -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(15),
                        new HealPartOfDmgDealt(0.5f));

            case SHIELD_ATTACK -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(10),
                    new SimpleDefendBehavior());

            case HYPNOSIS -> attack = new Attack(animal, attackName, 0.6f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.SLEEP));

            case GROWL -> attack = new Attack(animal, attackName, 1f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.FEAR, 2));

            case PEWK -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(5),
                    new InflictStatusBehavior(StatusID.POISON, 3));

            case HEALING_POWER -> attack = new Attack(animal, attackName, 1f, new DoNoDamageBehavior(),
                    new HealPercentageBehavior(1.2f));

            case TORNADO -> attack = new Attack(animal, attackName, 0.8f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.FEAR));

            case FLAMETHROWER -> attack = new Attack(animal, attackName, 0.8f, new SimpleDoDamageBehavior(25));

            case SPIT -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.FEAR, 3));

            case TSUNAMI -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(15));

            case RAGE -> {
                var stats = new EnumMap<StatID, Float>(StatID.class);
                stats.put(StatID.ATTACK, 1.25f);
                stats.put(StatID.DEFENSE, 0.9f);
                attack = new Attack(animal, attackName, 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }
            case FURY -> attack = new Attack(animal, attackName, 0.7f, new SimpleDoDamageBehavior(30),
                        new SelfInflictStatusBehavior(StatusID.PARALYSIS, 3));


            case HEADBUTT -> attack = new Attack(animal, attackName, 1f, new DoRandomDamage(1, 30));

            case PURR -> {
                var stats = new EnumMap<StatID, Float>(StatID.class);
                stats.put(StatID.ATTACK, 0.9f);
                stats.put(StatID.DEFENSE, 0.9f);
                attack = new Attack(animal, attackName,1f, new DoNoDamageBehavior(),
                        new AlterFoesStatsBehavior(stats));
            }

            case STOMP -> attack = new Attack(animal, attackName, 0.5f, new SimpleDoDamageBehavior(35),
                    new InflictStatusBehavior(StatusID.FEAR, 2));

            case GIFT_OF_LIFE -> {
                var stats = new EnumMap<StatID, Float>(StatID.class);
                stats.put(StatID.MAX_HEALTH, 1.25f);
                attack = new Attack(animal, attackName, 0.8f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case THUNDER -> attack = new Attack(animal, attackName, 0.8f, new SimpleDoDamageBehavior(10),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 2));

            case FRY -> attack = new Attack(animal, attackName, 1f, new SimpleDoDamageBehavior(5),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 3));

            case BONFIRE -> {
                var stats = new EnumMap<StatID, Float>(StatID.class);
                stats.put(StatID.ATTACK, 1.2f);
                attack = new Attack(animal, attackName, 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case ICESHIELD -> {
                var stats = new EnumMap<StatID, Float>(StatID.class);
                stats.put(StatID.DEFENSE, 1.2f);
                attack = new Attack(animal, attackName, 1f, new DoNoDamageBehavior(),
                        new AlterAllyStatsBehavior(stats));
            }

            case PERMASTINK -> attack = new Attack(animal, attackName, 0.8f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.POISON, 7));

            case INTIMIDATION -> attack = new Attack(animal, attackName, 1f, new DoNoDamageBehavior(),
                    new InflictStatusBehavior(StatusID.PARALYSIS, 5));

            //add here

            default -> attack = new Attack(animal, AttackEnum.DEFEND,  1f, new DoNoDamageBehavior());
        }
        return attack;
    }

    public static void addAttackToAnimal(Animal animal, AttackEnum attackName, ElementType type){
        Attack attack = addAttackToAnimal(animal, attackName);
        attack.setType(type);
    }
        
    /**
     * @return List of all the attacks that can be created by the factory.
     */
    public static List<IAttack> getAllAttacks(){
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
