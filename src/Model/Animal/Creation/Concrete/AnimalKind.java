package Model.Animal.Creation.Concrete;

import Model.Util.Position;

import java.net.URL;

public enum AnimalKind{
    DOG(100, 1, 1, 1, "An average stat animal.", "dog"),
    CAT(90, 1.05f, 0.95f, 1.1f, "A slightly offensive animal.", "cat"),
    SNAKE(75, 1.3f,0.7f, 1.2f, "A fast and offense-oriented animal.", "snake"),
    UNICORN(130, 0.75f, 1.15f, 0.8f,"A health and defense-oriented animal.", "unicorn"),
    HEDGEHOG(100, 1.05f, 1.15f, 0.8f, "Defends well and sends back half of the received damage when defending.", "hedgehog"),
    CLAM(85, 0.8f, 1.5f, 0.7f, "A defense-oriented animal : High defense, takes no damage when defending, but low stats otherwise.", "clam"),
    BEAVER(100, 1.2f, 0.7f, 0.9f, "", "beaver" )
    ;

    AnimalKind(final int maxHealth, final float attackStat, final float defenseStat, final float speedStat, String description, String url){
        this.maxHealth = maxHealth;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
        this.speedStat = speedStat;
        this.description = description;
        this.url = url;
    }

    private final int maxHealth;
    private final float attackStat;
    private final float defenseStat;
    private final float speedStat;

    private final String description;
    private final String url;


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

    /**
     * Gets the image URL for a given position, top or bottom.
     * @param position {@link Position}
     * @return A valid URL.
     */
    public URL getURL(Position position){
        String pos;
        if(position.equals(Position.BOTTOM))
            pos = "bottom";
        else pos = "top";
        return getClass().getResource(String.format("/resources/images/%s_%s.png", url, pos));
    }

    /**
     * Gets the default image url of the animal kind, which is the facing animal (top position).
     * @return Facing animal image URL.
     */
    public URL getURL(){
        return getURL(Position.TOP);
    }
}
