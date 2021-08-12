package Model.Animal.Behaviors.DefendBehavior.Concrete;

public enum DefendBehaviorEnum {
    SIMPLEDEFEND_BEHAVIOR("Simple defense", "Protects itself based on the defense stat and the current mode."),
    FULLDEFEND_BEHAVIOR("Full defense", String.format("%d%s chance to take no damage when defending.", FullDefendBehavior.SUCCESS_RATE, "%")),
    MIRRORDEFEND_BEHAVIOR("Mirror defense", "Return half of the damage to the opponent."),
    NODEFEND_BEHAVIOR("No defense", "Takes full damage."),
    HEALDEFND_BEHAVIOR("Healing defense", "Heals some HP when defending.")
    ;

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

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }
}
