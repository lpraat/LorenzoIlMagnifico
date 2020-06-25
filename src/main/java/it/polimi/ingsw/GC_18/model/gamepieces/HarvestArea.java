package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the harvest area in the board.
 */
public class HarvestArea extends ActionPlace implements Serializable {

    private static final long serialVersionUID = 6868148381825939062L;

    /**
     * Creates a new harvest area.
     */
    HarvestArea() {
        super(GameConfigLoader.loadPlaceBonus("harvestArea"), GameConfigLoader.loadPlaceValue("harvestArea"));
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.HARVEST_AREA;
    }
    
}
