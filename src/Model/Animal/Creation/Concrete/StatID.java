package Model.Animal.Creation.Concrete;

import java.io.Serializable;

public enum StatID implements Serializable {
    MAX_HEALTH("max health"),
    ATTACK("attack"),
    DEFENSE("defense"),
    ACCURACY("accuracy"),
    SPEED("speed")
    ;

    private final String name;

    StatID(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}


