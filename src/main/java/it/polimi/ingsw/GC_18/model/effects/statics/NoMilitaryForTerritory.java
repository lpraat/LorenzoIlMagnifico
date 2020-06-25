package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that let the Player taking territories
 * without satisfying the military requirement.
 */
public class NoMilitaryForTerritory implements StaticEffect, Serializable {
    
    private static final long serialVersionUID = -5235519553388045655L;

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("noMilitaryForTerritory", this);
    }

    @Override
    public String toString() {
        return "You can take territory cards without satisfying the military requirement";
    }
    
}
