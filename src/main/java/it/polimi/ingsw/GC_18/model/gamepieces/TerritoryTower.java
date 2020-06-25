package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the territory tower.
 */
public class TerritoryTower extends Tower<Territory> implements Serializable {
    
    private static final long serialVersionUID = -5448750247373200046L;

    /**
     * Creates a new building tower by creating all the floors.
     */
    public TerritoryTower() {
        super();
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("firstTerritoryFloor"),
                                    GameConfigLoader.loadPlaceValue("firstTerritoryFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("secondTerritoryFloor"),
                                    GameConfigLoader.loadPlaceValue("secondTerritoryFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("thirdTerritoryFloor"),
                                    GameConfigLoader.loadPlaceValue("thirdTerritoryFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("fourthTerritoryFloor"),
                                    GameConfigLoader.loadPlaceValue("fourthTerritoryFloor"), this));
    }
    
    @Override
    public TowerColor getColor() {
        return TowerColor.GREEN;
    }

}
