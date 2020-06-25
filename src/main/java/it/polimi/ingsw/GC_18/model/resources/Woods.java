package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;

/**
 * This class represents the woods resource.
 */
public class Woods extends Resource {
    
    private static final long serialVersionUID = 7793874728193040455L;

    /**
     * Creates a new woods type of resource.
     * @param value the number of woods.
     */
    public Woods(int value) {
        super(value);
    }

    /**
     * Adds the woods to the player and notifies that.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        player.addWoods(value, new NotifyResource(this, source));
    }

    /**
     * @param player the player to subtract the resource to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractWoods(value);
    }

    /**
     * @param player the player to set the resource.
     */
    @Override
    public void setPlayer(Player player) {
        player.setWoods(value);
    }

    @Override
    public ResourceType getType() {
        return ResourceType.WOODS;
    }

    @Override
    public String toString() {
        return "woods";
    }

    @Override
    public String toStringCli() {
        return "Woods";
    }
}
