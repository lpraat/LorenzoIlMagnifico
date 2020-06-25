package it.polimi.ingsw.GC_18.model.effects;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface represents a game effect that is static. This type of effect
 * will set a constraint to the player other part of the game can use to change the player's actions.
 */
public interface StaticEffect extends GameEffect {
    /**
     * @param player the player to add the effect to.
     */
    @Override
    default void add(Player player) {
        apply(player);
    }
}
