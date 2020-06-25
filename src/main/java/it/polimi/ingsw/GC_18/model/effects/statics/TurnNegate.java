package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that stops the Player from
 * doing his turn. The player can still take the action after all the players
 * have done their turns.
*/
public class TurnNegate implements StaticEffect, Serializable {

    private static final long serialVersionUID = 8301380186330875596L;

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("turnNegate", this);
    }

    @Override
    public String toString() {
        return "You skip your first turn, you can still take the action skipped ater all players have done their turns";
    }
}
