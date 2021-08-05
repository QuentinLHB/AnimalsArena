package Model.Action.Status.Concrete;

import java.util.Locale;

public enum StatusID {
    POISON,
    PARALYSIS,
    SLEEP,
    FEAR;

    /**
     * All lower case.
     * @return
     */
    public String lowerCaseName(){
        return name().toLowerCase(Locale.ROOT);
    }

    /**
     * Capital letter on the first character.
     * @return Status name with a capital letter on the first letter.
     */
    public String normalCase(){
        String capitalFirstLetter = Character.toString(name().toCharArray()[0]);
        String lowerCaseFirstLetter = capitalFirstLetter.toLowerCase(); //s
        return lowerCaseName().replaceFirst(lowerCaseFirstLetter, lowerCaseFirstLetter.toUpperCase(Locale.ROOT));
    }
}
