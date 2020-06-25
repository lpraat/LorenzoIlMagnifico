package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.Venture;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the venture tower.
 */
public class VentureTower extends Tower<Venture> implements Serializable{
    
    private static final long serialVersionUID = 6025576838703901257L;

    /**
     * Creates a new building tower by creating all the floors.
     */
    public VentureTower() {
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("firstVentureFloor"),
                                    GameConfigLoader.loadPlaceValue("firstVentureFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("secondVentureFloor"),
                                    GameConfigLoader.loadPlaceValue("secondVentureFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("thirdVentureFloor"),
                                    GameConfigLoader.loadPlaceValue("thirdVentureFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("fourthVentureFloor"),
                                    GameConfigLoader.loadPlaceValue("fourthVentureFloor"), this));
    }

    @Override
    public TowerColor getColor() {
        return TowerColor.PURPLE;
    }

}
