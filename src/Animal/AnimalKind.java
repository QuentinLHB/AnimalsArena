package Animal;

public enum AnimalKind{
    DOG(100, 10, 5),
    SNAKE(60, 30,4);

    private AnimalKind(final int maxHealth, final int attackStat, final int defenseStat){
        this.maxHealth = maxHealth;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
    }

    private final int maxHealth;
    private final int attackStat;
    private final int defenseStat;


    public int getMaxHealth(){
        return maxHealth;
    }

    public int getAttack(){
        return attackStat;
    }

    public int getDefense(){
        return defenseStat;
    }
}
