package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that negates the Player from receiving bonus by placing
 * a pawn in the the third and fourth floor of a tower.
 */
public class NoHighFloorBonus implements StaticEffect, Serializable {
    
    private static final long serialVersionUID = -4998707835822667352L;

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("noHighFloorBonus", this);
    }

    @Override
    public String toString() {
        return "You get no bonuses by placing a pawn in the third and fourth floor of a tower";
    }
    
}
