package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that gives the player
 * a servant malus when adding value with servants in an action.
 */
public class ServantMalus implements StaticEffect, Serializable {
    
    private static final long serialVersionUID = -507670721066305447L;
    
    private int value;

    /**
     * @param value the value of the malus.
     */
    public ServantMalus(int value) {
        this.value = value;
    }

    /**
     * @return the value of the malus.
     */
    public int getValue() {
        return value;
    }

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("servantMalus", this);
    }

    @Override
    public String toString() {
        return "You have to spend " + value + " servants instead of 1 for increasing the action value";
    }
    
}
