package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the production area in the board.
 */
public class ProductionArea extends ActionPlace implements Serializable {

    private static final long serialVersionUID = 9213177952569152034L;

    /**
     * Creates a new production area.
     */
    ProductionArea() {
        super(GameConfigLoader.loadPlaceBonus("productionArea"), GameConfigLoader.loadPlaceValue("productionArea"));
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.PRODUCTION_AREA;
    }
    
}