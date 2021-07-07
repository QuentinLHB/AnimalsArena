package Animal.Creation.Concrete;

public enum AnimalKind{
    DOG(100, 1, 1, 1, "Average stats"),
    CAT(90, 1.15f, 1, 1.3f, "Average stats"),
    SNAKE(70, 1.3f,0.8f, 0.7f, "Low health and defense, high attack"),
    UNICORN(130, 0.9f, 1.15f, 0.6f,"Low attack, high defense and health"),
    HEDGEHOG(100, 1.08f, 1.15f, 0.7f, "Defend well, sends back half damage in defense mode"),
    CLAM(55, 0.8f, 1.3f, 0.5f, "Takes no damage when defending, but low health and attack.")

    ;

    private AnimalKind(final int maxHealth, final float attackStat, final float defenseStat, final float speedStat, String description){
        this.maxHealth = maxHealth;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
        this.speedStat = speedStat;
        this.description = description;


    }

    private final int maxHealth;
    private final float attackStat;
    private final float defenseStat;
    private final float speedStat;

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

    public float getSpeed(){
        return speedStat;
    }


    public String getDescription(){return description;}
}
