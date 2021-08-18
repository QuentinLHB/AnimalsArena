package Model.Animal.Behaviors.DieBehavior.Concrete;

import Model.Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;

public enum DieBehaviorEnum {
    SIMPLEDIE_BEHAVIOR("Simple death", "Dies when has no remaining HP."),
    UNDEADDIE_BEHAVIOR("Undead death", "Is reborn with half HP.");

    DieBehaviorEnum(final String name, final String description){
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
