package it.polimi.ingsw.GC_18.model.effects;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface represents an effect that will be activated at the end of the game
 * and will modify the end game scores.
 */
public interface EndgameEffect extends PermanentEffect {
    /**
     * @param player the player to add the effect to.
     */
    @Override
    default void add(Player player) {
        player.getPersonalBoard().getEndgameEffects().add(this);
    }
}
