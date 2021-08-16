package Model.Action.Attack.Concrete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Enumerator of the possible attacks.
 */
public enum AttackEnum {

    DEFEND("Defend"),

    //Normal attacks
    BITE("Bite"),
    TORNADO("Tornado"),
    FURY("Fury"),
    STOMP("Stomp"),
    INTIMIDATION("Intimidation"),
    HEADBUTT("Headbutt"),
    SHIELD_ATTACK("Shield attack"),

    //Status attacks
    HYPNOSIS("Hypnosis"),
    GROWL("Growl"),

    // Stat altering moves
    PURR("Purr"),
    RAGE("Rage"),
    HEALING_POWER("Healing Power"),

    //Fire attacks
    FIRE_BITE("Fire Bite"),
    FLAMETHROWER("Flamethrower"),
    BONFIRE("Bonfire"),

    //Poison attacks
    POISON_BITE("Poison Bite"),
    PEWK("Pewk"),
    PERMASTINK("Perma-stink"),

    // Water attacks
    SPIT("Spit"),
    TSUNAMI("Tsunami"),
    ICESHIELD("Ice Shied"),

    // Electric attacks
    THUNDER("Thunder"),
    FRY("Fry"),

    //Undead attacks
    DEATH_BITE("Death Bite"),
    GIFT_OF_LIFE("Gift of Life")


    ;

    private final String name;

    AttackEnum(final String name ){
        this.name = name;
    }

    /**
     * Returns the attack's name.
     * @return attack's name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the attack's description.
     * @return attack's description
     */
    public String getDescription(){
        return AttackFactory.getAttackDescription(this);
    }

    public static AttackEnum[] getSortedAttacks(){
        var attacks = values().clone();
        Arrays.sort(attacks, Comparator.comparing(o -> o.name));
        return attacks;
    }
}
