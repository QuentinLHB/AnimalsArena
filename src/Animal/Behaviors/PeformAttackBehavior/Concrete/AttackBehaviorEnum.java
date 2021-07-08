package Animal.Behaviors.PeformAttackBehavior.Concrete;

public enum AttackBehaviorEnum {
    SIMPLE_BEHAVIOR("Simple attack", "Inflict damage to the opponent."),
    UNDEAD_BEHAVIOR("Undead attack", "Inflict damage both the opponent and itself (half of it)");

    AttackBehaviorEnum(final String name, final String description){
        this.name = name;
        this.description = description;
    }

    private final String name;
    private final String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
