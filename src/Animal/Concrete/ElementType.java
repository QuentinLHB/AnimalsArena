package Animal.Concrete;

public enum ElementType {
    NORMAL(1, 1, 1),
    FIRE(1.1f, 1.1f, 1.05f),
    POISON(0.7f, 1.3f, 0.8f),
    WATER(1.3f, 0.7f, 1.2f);

    private ElementType(final float healthVariation, final float attackVariation, final float defenseVariation) {
        this.healthVariation = healthVariation;
        this.attackVariation = attackVariation;
        this.defenseVariation = defenseVariation;
    }

    private final float healthVariation;
    private final float attackVariation;
    private final float defenseVariation;


    public float getHealthVariation() {
        return healthVariation;
    }

    public float getAttackVariation() {
        return attackVariation;
    }

    public float getDefenseVariation() {
        return defenseVariation;
    }
}
