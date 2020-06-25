package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the building tower.
 */
public class BuildingTower extends Tower<Building> implements Serializable{
    
    private static final long serialVersionUID = 7335272074521925808L;

    /**
     * Creates a new building tower by creating all the floors.
     */
    public BuildingTower() {
        super();
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("firstBuildingFloor"),
                                    GameConfigLoader.loadPlaceValue("firstBuildingFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("secondBuildingFloor"),
                                    GameConfigLoader.loadPlaceValue("secondBuildingFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("thirdBuildingFloor"),
                                    GameConfigLoader.loadPlaceValue("thirdBuildingFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("fourthBuildingFloor"),
                                    GameConfigLoader.loadPlaceValue("fourthBuildingFloor"), this));
    }
    
    @Override
    public TowerColor getColor() {
        return TowerColor.YELLOW;
    }
    
}
