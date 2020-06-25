package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents the spot in the market that gives military points
 */
public class MilitarySpot extends ActionPlace implements Serializable {

    private static final long serialVersionUID = 3920121232816482680L;

    /**
     * Creates a new military spot.
     * @param placeBonus the resouces bonus of the spot.
     * @param placeValue the value needed for placing in this spot.
     */
    MilitarySpot(Resources placeBonus, int placeValue) {
        super(placeBonus, placeValue);
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.MILITARY_SPOT;
    }
    
}
