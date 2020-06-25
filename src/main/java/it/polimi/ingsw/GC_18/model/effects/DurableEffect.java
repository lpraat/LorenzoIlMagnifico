package it.polimi.ingsw.GC_18.model.effects;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This class represents an effect that will be added to the player personal board's effects.
 */
public interface DurableEffect extends Effect {
    /**
     * @param player the player to add the effect to.
     */
    void add(Player player);
}
