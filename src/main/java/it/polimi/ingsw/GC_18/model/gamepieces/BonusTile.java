package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.GameMode;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the bonus tile given to a player.
 */
public class BonusTile implements Serializable {
   
    private static final long serialVersionUID = 4137441899036660929L;
    
    private Resources harvestBonus;
    private Resources productionBonus;

    /**
     * Sets the bonus tiles to the players according to their index in a game instance.
     * @param index the index of the player.
     * @param gameMode the game mode.
     */
    public BonusTile(GameMode gameMode, int index) {
        if (gameMode.equals(GameMode.ADVANCED)) {
            productionBonus = GameConfigLoader.loadAdvancedBonusTile("productionBonus", index);
            harvestBonus = GameConfigLoader.loadAdvancedBonusTile("harvestBonus", index);
        }
    }

    /**
     * @return the harvest bonus.
     */
    public Resources getHarvestBonus() {
        return harvestBonus;
    }

    /**
     * @return the production bonus.
     */
    public Resources getProductionBonus() {
        return productionBonus;
    }
}
