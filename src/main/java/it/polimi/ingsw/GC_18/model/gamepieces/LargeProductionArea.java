package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents the large production area in the board.
 */
public class LargeProductionArea extends ActionPlace implements Serializable {
    
    private static final long serialVersionUID = -718338783541208905L;

    /**
     * Creates a new large production area.
     * @param placeBonus the resources bonus of this area.
     * @param placeValue the value needed for placing in this spot.
     */
    LargeProductionArea(Resources placeBonus, int placeValue) {
        super(placeBonus, placeValue);
        this.pawns = new ArrayList<>();
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.LARGEPRODUCTION_AREA;
    }
    
}
