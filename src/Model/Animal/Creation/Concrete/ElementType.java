package Model.Animal.Creation.Concrete;

public enum ElementType {
    NORMAL(1, 1, 1, 1),
    FIRE(0.85f, 1.1f, 0.9f, 1.2f),
    POISON(0.95f, 1.3f, 0.8f, 0.95f),
    WATER(1.3f, 0.7f, 1.2f, 0.8f),
    ELETRIC(1, 1.1f, 0.9f, 1.5f),
    UNDEAD(0.6f, 1, 1, 0.5f);

    private ElementType(final float healthVariation, final float attackVariation, final float defenseVariation, float speedVariation) {
        this.healthVariation = healthVariation;
        this.attackVariation = attackVariation;
        this.defenseVariation = defenseVariation;
        this.speedVariation = speedVariation;
    }

    private final float healthVariation;
    private final float attackVariation;
    private final float defenseVariation;
    private final float speedVariation;



    public float getHealthVariation() {
        return healthVariation;
    }

    public float getAttackVariation() {
        return attackVariation;
    }

    public float getDefenseVariation() {
        return defenseVariation;
    }
    public float getSpeedVariation() {
        return speedVariation;
    }

}
