package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;

/**
 * This class represents the faith points resource.
 */
public class FaithPoints extends Resource {
    
    private static final long serialVersionUID = -4391396999689586156L;

    /**
     * Create a new faith point resource type.
     * @param value the faith points number.
     */
    public FaithPoints(int value) {
        super(value);
    }

    /**
     * Adds the faith points to the player and notifies that.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        player.addFaithPoints(value, new NotifyResource(this, source));
    }

    /**
     * @param player the player to set the faith points
     */
    @Override
    public void setPlayer(Player player) {
        player.setFaithPoints(value);
    }

    /**
     * @param player the player to subtract the faith points to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractFaithPoints(value);
    }

    @Override
    public ResourceType getType() {
        return ResourceType.FAITH_POINTS;
    }

    @Override
    public String toString() {
        return "faithPoints";
    }

    @Override
    public String toStringCli() {
        return "Faith Points";
    }
}
