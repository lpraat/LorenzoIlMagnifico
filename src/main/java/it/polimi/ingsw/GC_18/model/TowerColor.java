package it.polimi.ingsw.GC_18.model;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This enum represents all the possible floors to place according to a color.
 */
public enum TowerColor {
    GREEN(new ExtraFloor[]{ExtraFloor.TERRITORY1, ExtraFloor.TERRITORY2, ExtraFloor.TERRITORY3, ExtraFloor.TERRITORY4}),
    BLUE(new ExtraFloor[]{ExtraFloor.CHARACTER1, ExtraFloor.CHARACTER2, ExtraFloor.CHARACTER3, ExtraFloor.CHARACTER4}),
    PURPLE(new ExtraFloor[]{ExtraFloor.VENTURE1, ExtraFloor.VENTURE2, ExtraFloor.VENTURE3, ExtraFloor.VENTURE4}),
    YELLOW(new ExtraFloor[]{ExtraFloor.BUILDING1, ExtraFloor.BUILDING2, ExtraFloor.BUILDING3, ExtraFloor.BUILDING4}),

    // any has all the values of the precedent colors
    ANY(Stream.concat(Stream.concat(Stream.concat(Arrays.stream(PURPLE.getExtraFloors()), Arrays.stream(YELLOW.getExtraFloors())),
                    Arrays.stream(GREEN.getExtraFloors())), Arrays.stream(BLUE.getExtraFloors())).toArray(ExtraFloor[]::new));

    private ExtraFloor[] extraFloors;

    /**
     * Creates a new TowerColor.
     * @param extraFloors the extra floors of this color.
     */
    TowerColor(ExtraFloor[] extraFloors) {
        this.extraFloors = extraFloors;
    }

    /**
     * @return the extra floors.
     */
    public ExtraFloor[] getExtraFloors() {
        return extraFloors;
    }
}
