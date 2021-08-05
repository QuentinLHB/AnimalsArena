package Model.playerAI.Concrete;
import Model.Animal.Creation.Concrete.Animal;
import Model.Util.Position;

public class Player {

    /**
     * Id (1 or 2)
     */
    protected static int Id = 1;
    protected final int id;

    /**
     * Position on the battle screen.
     */
    protected Position position;
    public Position getPosition(){return position;}
    
    public Position getOppositePosition(){
        return position.equals(Position.TOP) ? Position.BOTTOM : Position.TOP;
    }

    protected Animal ally;

    protected Animal foe;

    public Player(Position position, Animal ally, Animal foe){
        this(position);
        this.ally = ally;
        this.foe = foe;
    }

    public Player(Position position){
        this.position = position;
        this.id = Id;
        if(++Id > 2) Id = 1;
    }

//    public void performAttack()

    public boolean isBot(){
        return false;
    }

    public Animal getAlly() {
        return ally;
    }

    public void setAlly(Animal ally) {
        this.ally = ally;
    }

    public Animal getFoe() {
        return foe;
    }

    public void setFoe(Animal foe) {
        this.foe = foe;
    }

    @Override
    public String toString() {
        return "Player " + id;
    }
}
