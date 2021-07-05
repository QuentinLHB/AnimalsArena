package Animal.Creation.Concrete;

public enum AnimalKind{
    DOG(100, 10, 5, "Average stats"),
    CAT(90, 15, 10, "Average stats"),
    SNAKE(70, 30,4, "Low health and defense, high attack"),
    UNICORN(130, 8, 15, "Low attack, high defense and health"),
    HEDGEHOG(100, 8, 15, "Defend well, sends back half damage in defense mode"),
    CLAM(55, 8, 30, "Takes no damage when defending, but low health and attack.")

    ;

    private AnimalKind(final int maxHealth, final int attackStat, final int defenseStat, String description){
        this.maxHealth = maxHealth;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
        this.description = description;

    }

    private final int maxHealth;
    private final int attackStat;
    private final int defenseStat;
    private final String description;


    public int getMaxHealth(){
        return maxHealth;
    }

    public int getAttack(){
        return attackStat;
    }

    public int getDefense(){
        return defenseStat;
    }

    public String getDescription(){return description;}
}
