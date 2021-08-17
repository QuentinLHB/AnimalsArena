package Model.Action.Status.Concrete;

import java.util.Locale;

public enum StatusID {
    POISON("[PSN]", "Hurts the animal at the end of the turn."),
    PARALYSIS("[PAR]", "Disables two random attacks."),
    SLEEP("[SLP]", "Prevents the animal from performing any attack."),
    FEAR("[FEAR]", "Reduces attack and defense stats by half.");

    StatusID(String initials, String desc){
        this.initials = initials;
        this.desc = desc;
    }

    private final String initials;
    private final String desc;

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

    public String getDesc() {
        return desc;
    }
}
