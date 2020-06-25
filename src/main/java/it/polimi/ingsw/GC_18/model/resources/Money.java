package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;

/**
 * This class represents the money resource.
 */
public class Money extends Resource {

    private static final long serialVersionUID = 1349057819625430509L;

    /**
     * Creates a new money type of resource.
     * @param value the number of the money.
     */
    public Money(int value) {
        super(value);
    }


    /**
     * Adds the money to the player and notifies that.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        player.addMoney(value, new NotifyResource(this, source));
    }

    /**
     * @param player the player to set the money.
     */
    @Override
    public void setPlayer(Player player) {
        player.setMoney(value);
    }

    /**
     * @param player the player to subtract the money to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractMoney(value);

    }

    @Override
    public ResourceType getType() {
        return ResourceType.MONEY;
    }

    @Override
    public String toString() {
        return "money";
    }

    @Override
    public String toStringCli() {
        return "Money";
    }
}

