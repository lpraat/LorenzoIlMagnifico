package it.polimi.ingsw.GC_18.model;

/**
 * Represents the different type of floor place a player can place a pawn in.
 */
public enum ExtraFloor {
    CHARACTER1("CHARACTER1"), CHARACTER2("CHARACTER2"), CHARACTER3("CHARACTER3"), CHARACTER4("CHARACTER4"),
    BUILDING1("BUILDING1"), BUILDING2("BUILDING2"), BUILDING3("BUILDING3"), BUILDING4("BUILDING4"),
    VENTURE1("VENTURE1"), VENTURE2("VENTURE2"), VENTURE3("VENTURE3"), VENTURE4("VENTURE4"),
    TERRITORY1("TERRITORY1"), TERRITORY2("TERRITORY2"), TERRITORY3("TERRITORY3"), TERRITORY4("TERRITORY4");


    private String name;

    /**
     * @param name the name of the floor.
     */
    ExtraFloor(String name) {
        this.name = name;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }
}
