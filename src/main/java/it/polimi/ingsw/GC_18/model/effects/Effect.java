package it.polimi.ingsw.GC_18.model.effects;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface represents a generic effect in the game.
 */
public interface Effect extends Serializable {
    /**
     * Applies the effect to the player.
     * @param player the player the effect is applied to.
     */
    void apply(Player player);
}
