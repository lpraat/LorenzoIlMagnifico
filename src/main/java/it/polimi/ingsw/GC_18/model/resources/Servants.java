package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;

/**
 * This class represents the servants resource.
 */
public class Servants extends Resource {
    
    private static final long serialVersionUID = -1612920038994756728L;

    /**
     * Create a new servants type of resource.
     * @param value the number of servants.
     */
    public Servants(int value) {
        super(value);
    }

    /**
     * Adds the servants to the player and notifies that.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        player.addServants(value, new NotifyResource(this, source));
    }

    /**
     * @param player the player to set the servants.
     */
    @Override
    public void setPlayer(Player player) {
        player.setServants(value);
    }

    /**
     * @param player the player to subtract the servants to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractServants(value);

    }

    @Override
    public ResourceType getType() {
        return ResourceType.SERVANTS;
    }

    @Override
    public String toString() {
        return "servants";
    }

    @Override
    public String toStringCli() {
        return "Servants";
    }
}
