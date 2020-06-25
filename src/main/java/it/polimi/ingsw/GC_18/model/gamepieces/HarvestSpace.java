package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the harvest space in the board, which is composed by a harvest area and a large
 * harvest area.
 */
public class HarvestSpace implements Serializable {

    private static final long serialVersionUID = -7275768833531902829L;

    private HarvestArea harvestArea;
    private LargeHarvestArea largeHarvestArea;

    /**
     * Creates a new harvest space according to the number of players. If the number of players is not enough
     * there may not be a large harvest place.
     * @param numPlayers the number of players of the game.
     */
    public HarvestSpace(int numPlayers) {
        harvestArea = new HarvestArea();
        if (numPlayers != 2) {
            this.largeHarvestArea = new LargeHarvestArea(GameConfigLoader.loadPlaceBonus("largeHarvestArea"),
                    GameConfigLoader.loadPlaceValue("largeHarvestArea"));
        }
    }

    /**
     * @return the harvest area.
     */
    public HarvestArea getHarvestArea() {
        return harvestArea;
    }

    /**
     * @return the large harvest area.
     */
    public LargeHarvestArea getLargeHarvestArea() {
        return largeHarvestArea;
    }

}
