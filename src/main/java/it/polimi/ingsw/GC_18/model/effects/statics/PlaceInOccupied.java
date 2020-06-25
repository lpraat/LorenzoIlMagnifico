package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that let the Player to place
 * in an occupied space.
 */
public class PlaceInOccupied implements StaticEffect, Serializable {
    
    private static final long serialVersionUID = -5957972408729179118L;

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("placeInOccupied", this);
    }

    @Override
    public String toString() {
        return "You can place your pawn in occupied spaces";
    }
    
}
