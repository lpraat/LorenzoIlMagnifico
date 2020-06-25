package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;

/**
 * This class represents the victory points resource.
 */
public class VictoryPoints extends Resource {
    
    private static final long serialVersionUID = 6163521531916019381L;

    /**
     * Creates a new victory points type of resource.
     * @param value the number of victory points.
     */
    public VictoryPoints(int value) {
        super(value);
    }

    /**
     * Adds the victory points to the player and notifies that.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        player.addVictoryPoints(value, new NotifyResource(this, source));
    }

    /**
     * @param player the player to set the resource.
     */
    @Override
    public void setPlayer(Player player) {
        player.setVictoryPoints(value);
    }

    /**
     * @param player the player to subtract the resource to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractVictoryPoints(value);

    }

    @Override
    public ResourceType getType() {
        return ResourceType.VICTORY_POINTS;
    }

    @Override
    public String toString() {
        return "victoryPoints";
    }

    @Override
    public String toStringCli() {
        return "Victory Points";
    }
}

