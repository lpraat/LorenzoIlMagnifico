package it.polimi.ingsw.GC_18.model.effects;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface represents a once per turn effect in the game.
 */
public interface OncePerTurnEffect extends DurableEffect {
    /**
     * @param player the player to add the effect to.
     */
    @Override
    default void add(Player player) {
        player.getPersonalBoard().getOncePerTurnEffects().add(this);
    }

    /**
     * @return true if the effect has not been activated yet this turn.
     */
    boolean isActivable();

    /**
     * Sets the effect activation.
     * @param activated boolean indicating the activation.
     */
    void setActivated(boolean activated);

}
