package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents the large harvest area in the baord.
 */
public class LargeHarvestArea extends ActionPlace implements Serializable {

    private static final long serialVersionUID = -951201156369679677L;

    /**
     * Creates a new large harvest area.
     * @param placeBonus the resources bonus of this area.
     * @param placeValue the value needed for placing in this spot.
     */
    LargeHarvestArea(Resources placeBonus, int placeValue) {
        super(placeBonus, placeValue);
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.LARGEHARVEST_AREA;
    }

}
