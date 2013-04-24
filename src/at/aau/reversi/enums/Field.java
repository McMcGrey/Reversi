package at.aau.reversi.enums;

/**
 * Field enum
 * Possible field values
 */
public enum Field {
    /**
     * Empty field
     */
    EMPTY,
    /**
     * White field
     */
    WHITE,
    /**
     * Black field
     */
    BLACK,
    /**
     * Possible field
     */
    MAYBE;

    @Override
    public String toString() {
        switch (ordinal()){
            case 0:
                return "_";
            case 1:
                return "W";
            case 2:
                return "B";
            case 3:
                return "M";
        }
        return "";
    }


}

