package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the character tower.
 */
public class CharacterTower extends Tower<Character> implements Serializable {
    
    private static final long serialVersionUID = 5241382106364022745L;

    /**
     * Creates a new character tower by creating all the floors.
     */
    public CharacterTower() {
        super();
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("firstCharacterFloor"),
                                    GameConfigLoader.loadPlaceValue("firstCharacterFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("secondCharacterFloor"),
                                    GameConfigLoader.loadPlaceValue("secondCharacterFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("thirdCharacterFloor"),
                                    GameConfigLoader.loadPlaceValue("thirdCharacterFloor"), this));
        this.floors.add(new Floor<>(GameConfigLoader.loadPlaceBonus("fourthCharacterFloor"),
                                    GameConfigLoader.loadPlaceValue("fourthCharacterFloor"), this));
    }

    @Override
    public TowerColor getColor() {
        return TowerColor.BLUE;
    }
    
}
