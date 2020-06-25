package it.polimi.ingsw.GC_18.model.effects;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This class represents an effect that is a part of an harvest chain.
 */
public interface HarvestEffect extends ChainEffect {
    /**
     * @param player the player to add the effect to.
     */
    @Override
    default void add(Player player) {
        player.getPersonalBoard().getHarvestEffects().add(this);
    }
}
