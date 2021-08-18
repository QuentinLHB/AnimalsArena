package Model.Animal.Behaviors.PeformAttackBehavior.Concrete;

import View.Util;

public enum AttackBehaviorEnum {
    SIMPLE_BEHAVIOR("Simple attack", "Inflict damage to the opponent."),
    SELFHARMING_BEHAVIOR("Self-harming attack", "Self-inflict half of the damage dealt.");

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

    @Override
    public String toString() {
        return Util.toHtml(String.format("%s: %s", name, description));
    }
}
