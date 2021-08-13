package Model.Action.Status.Concrete;

import java.util.Locale;

public enum StatusID {
    POISON("[PSN]"),
    PARALYSIS("[PAR]"),
    SLEEP("[SLP]"),
    FEAR("[FEAR]");

    StatusID(String initials){
        this.initials = initials;
    }

    private final String initials;
    /**
     * All lower case.
     * @return Status name in lower case.
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

    /**
     *
     * @return a few letters describing the status, such as [PSN] (Poison)
     */
    public String initials(){
        return initials;
    }
}
