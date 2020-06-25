package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the production space in the board, which is composed by a production area and a large
 * production area.
 */
public class ProductionSpace implements Serializable {
   
    private static final long serialVersionUID = 7830332468163602446L;
    
    private ProductionArea productionArea;
    private LargeProductionArea largeProductionArea;

    /**
     * Creates a new production space according to the number of players. If the number of players is not enough
     * there may not be a large production place.
     * @param numPlayers the number of players of the game.
     */
    public ProductionSpace(int numPlayers) {
        productionArea = new ProductionArea();
        if (numPlayers != 2) {
            largeProductionArea = new LargeProductionArea(GameConfigLoader.loadPlaceBonus("largeProductionArea"),
                    GameConfigLoader.loadPlaceValue("largeProductionArea"));
        }
    }

    /**
     * @return the production area.
     */
    public ProductionArea getProductionArea() {
        return productionArea;
    }

    /**
     * @return the large production area.
     */
    public LargeProductionArea getLargeProductionArea() {
        return largeProductionArea;
    }
    
}
