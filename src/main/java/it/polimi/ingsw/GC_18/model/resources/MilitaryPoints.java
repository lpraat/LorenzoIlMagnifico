package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;

/**
 * This class represents the military points resource.
 */
public class MilitaryPoints extends Resource {
    
    private static final long serialVersionUID = 2799807564086684849L;

    /**
     * Create a new military points type of resource.
     * @param value the military points number.
     */
    public MilitaryPoints(int value) {
        super(value);
    }

    /**
     * Adds the military points to the player and notifies that.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        player.addMilitaryPoints(value, new NotifyResource(this, source));

    }

    /**
     * @param player the player to set the military points.
     */
    @Override
    public void setPlayer(Player player) {
        player.setMilitaryPoints(value);
    }

    /**
     * @param player the player to subtract the military points to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractMilitaryPoints(value);

    }

    @Override
    public ResourceType getType() {
        return ResourceType.MILITARY_POINTS;
    }

    @Override
    public String toString() {
        return "militaryPoints";
    }

    @Override
    public String toStringCli() {
        return "Military Points";
    }
}
