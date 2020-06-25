package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;

/**
 * This class represents the stones resource.
 */
public class Stones extends Resource {
    
    private static final long serialVersionUID = -4704292810365680013L;

    /**
     * Creates a new stones resource type.
     * @param value the number of stones
     */
    public Stones(int value) {
        super(value);
    }

    /**
     * Adds the stones to the player and notifies that.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        player.addStones(value, new NotifyResource(this, source));
    }


    /**
     * @param player the player to set the stones
     */
    @Override
    public void setPlayer(Player player) {
        player.setStones(value);
    }

    /**
     * @param player the player to subtract the stones to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractStones(value);
    }

    @Override
    public ResourceType getType() {
        return ResourceType.STONES;
    }

    @Override
    public String toString() {
        return "stones";
    }

    @Override
    public String toStringCli() {
        return "Stones";
    }
}

