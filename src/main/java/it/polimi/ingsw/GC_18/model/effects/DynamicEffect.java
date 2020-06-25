package it.polimi.ingsw.GC_18.model.effects;

import java.util.Observer;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface represents a game effect that is dynamic. This type of effect uses
 * an observer pattern to dynamically change the player's action.
 */
public interface DynamicEffect extends GameEffect, Observer {

    /**
     * @param player the player to add the effect to.
     */
    @Override
    default void add(Player player) {
        player.getPersonalBoard().getDynamicEffects().add(this);
        this.apply(player);
    }

    /**
     * @param player the player the effect is applied to.
     */
    @Override
    default void apply(Player player) {
        player.addObserver(this);
    }
}
