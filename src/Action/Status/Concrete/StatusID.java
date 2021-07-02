package Action.Status.Concrete;

import java.util.Locale;

public enum StatusID {
    POISON,
    PARALYSIS,
    SLEEP;

    public String lowerCaseName(){
        return name().toLowerCase(Locale.ROOT);
    }
}
