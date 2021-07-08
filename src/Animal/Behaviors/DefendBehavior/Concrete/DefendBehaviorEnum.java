package Animal.Behaviors.DefendBehavior.Concrete;

public enum DefendBehaviorEnum {
    SIMPLE_BEHAVIOR("Simple defense", "Protects itself based on the defense stat and the current mode."),
    FULLDEFEND_BEHAVIOR("Full defense", "Takes no damage when in Defense Mode."),
    MIRRORDEFEND_BEHAVIOR("Mirror defense", "Return half of the damage to the opponent."),
    NODEFEND_BEHAVIOR("No defense", "Takes full damage.");

    DefendBehaviorEnum(final String name, final String description){
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
