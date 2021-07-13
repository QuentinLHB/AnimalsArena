package Animal.Behaviors.DieBehavior.Concrete;

public enum DieBehaviorEnum {
    SIMPLE_BEHAVIOR("Simple death", "Dies when has no remaining HP."),
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

}
