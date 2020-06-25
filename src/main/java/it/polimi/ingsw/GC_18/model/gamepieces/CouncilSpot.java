package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents the spot in the market that gives council privileges as the default bonus.
 */
public class CouncilSpot extends ActionPlace implements Serializable {

    private static final long serialVersionUID = -933650844039038973L;

    /**
     * Creates a new council spot.
     * @param placeBonus the resources bonus of the spot.
     * @param placeValue the value needed for placing in this spot.
     */
    CouncilSpot(Resources placeBonus, int placeValue) {
        super(placeBonus, placeValue);
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.COUNCIL_SPOT;
    }
    
}
