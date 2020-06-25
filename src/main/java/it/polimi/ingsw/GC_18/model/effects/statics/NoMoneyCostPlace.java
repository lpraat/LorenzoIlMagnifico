package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that let the Player not to spend
 * the money for placing in a Tower.
 */
public class NoMoneyCostPlace implements StaticEffect, Serializable {
    
    private static final long serialVersionUID = 8974388551637079895L;

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("noMoneyCostPlace", this);
    }

    @Override
    public String toString() {
        return "You don't have to spend money for placing in a occupied tower";
    }
    
}
