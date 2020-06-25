package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents the spot in the market that gives money as the default bonus.
 */
public class CoinSpot extends ActionPlace implements Serializable {

    private static final long serialVersionUID = 8401694870996586505L;

    /**
     * Creates a new coin spot.
     * @param placeBonus the resources bonus of the spot.
     * @param placeValue the value needed for placing in this spot.
     */
    CoinSpot(Resources placeBonus, int placeValue) {
        super(placeBonus, placeValue);
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.COIN_SPOT;
    }
    
}
