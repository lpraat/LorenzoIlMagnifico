package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents the spot in the market that gives servants as the default bonus.
 */
public class ServantSpot extends ActionPlace implements Serializable {
    
    private static final long serialVersionUID = 2300194338201191542L;

    /**
     * Creates a new servant spot.
     * @param placeBonus the resources bonuf of the spot.
     * @param placeValue the value needed for placing in this spot.
     */
    ServantSpot(Resources placeBonus, int placeValue) {
        super(placeBonus, placeValue);
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.SERVANT_SPOT;
    }
    
}
