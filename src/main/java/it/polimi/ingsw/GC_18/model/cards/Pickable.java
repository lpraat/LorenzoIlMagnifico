package it.polimi.ingsw.GC_18.model.cards;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface is implemented by all the Cards that can be picked up by a Player
 */
public interface Pickable {
    /**
     * @param player the Player that picks up the card
     */
    void add(Player player);

    /**
     *
     * @param player the Player that wants to pick up the card
     * @return true if player has enough space
     */
    boolean spaceExists(Player player);
}
