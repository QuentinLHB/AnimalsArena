package Model.playerAI.Concrete;

import Model.Animal.Creation.Abstract.IAnimal;
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

    protected IAnimal ally;

    protected IAnimal foe;

    public Player(Position position, IAnimal ally, IAnimal foe){
        this(position);
        this.ally = ally;
        this.foe = foe;
    }

    public Player(Position position){
        this.position = position;
        this.id = Id;
        if(++Id > 2) Id = 1;
    }

    public boolean isBot(){
        return false;
    }

    public IAnimal getAlly() {
        return ally;
    }

    public void setAlly(IAnimal ally) {
        this.ally = ally;
    }

    public IAnimal getFoe() {
        return foe;
    }

    public void setFoe(IAnimal foe) {
        this.foe = foe;
    }

    @Override
    public String toString() {
        return "Player " + id;
    }
}
