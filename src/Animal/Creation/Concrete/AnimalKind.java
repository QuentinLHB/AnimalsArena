package Animal.Creation.Concrete;

public enum AnimalKind{
    DOG(100, 1, 1, "Average stats"),
    CAT(90, 1.15f, 1, "Average stats"),
    SNAKE(70, 1.3f,0.8f, "Low health and defense, high attack"),
    UNICORN(130, 0.9f, 1.15f, "Low attack, high defense and health"),
    HEDGEHOG(100, 1.08f, 1.15f, "Defend well, sends back half damage in defense mode"),
    CLAM(55, 0.8f, 1.3f, "Takes no damage when defending, but low health and attack.")

    ;

    private AnimalKind(final int maxHealth, final float attackStat, final float defenseStat, String description){
        this.maxHealth = maxHealth;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
        this.description = description;

    }

    private final int maxHealth;
    private final float attackStat;
    private final float defenseStat;
    private final String description;


    public int getMaxHealth(){
        return maxHealth;
    }

    public float getAttack(){
        return attackStat;
    }

    public float getDefense(){
        return defenseStat;
    }

    public String getDescription(){return description;}
}
