package it.polimi.ingsw.GC_18.model.effects;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface represents a permanent effect that modifies the player actions.
 * A game effect is activated during the game run.
 */
public interface GameEffect extends PermanentEffect {
    /**
     * @param player the player to add the effect to.
     */
    @Override
    default void add(Player player) {
        player.getPersonalBoard().getGameEffects().add(this);
        apply(player);
    }
}
