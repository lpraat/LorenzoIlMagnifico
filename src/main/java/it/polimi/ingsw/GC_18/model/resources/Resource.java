package it.polimi.ingsw.GC_18.model.resources;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;

/**
 * This abstract class represent a generic resource.
 */
public abstract class Resource implements Serializable {
    
    private static final long serialVersionUID = 7383256574820337180L;
    int value;

    /**
     * Creates a new resource given the value.
     * @param value the number of the resource.
     */
    Resource(int value) {
        this.value = value;
    }

    /**
     * @return the resource quantity.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the resource quantity.
     * @param value the value to set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Increase the quantity.
     * @param value the value of the increase.
     */
    public void add(int value) {
        this.value += value;
    }

    /**
     * Decrease the quantity.
     * @param value the value of the decrease.
     */
    public void subtract(int value) {
        this.value -= value;
    }

    /**
     * Adds the resource to the player.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    public abstract void addPlayer(Player player, Source source);

    /**
     * Set the player resource of this type.
     * @param player the player to set the resource.
     */
    public abstract void setPlayer(Player player);

    /**
     * Subtract the resource to the player.
     * @param player the player to subtract the resource to.
     */
    public abstract void subtractPlayer(Player player);

    /**
     * @return the type of resource.
     */
    public abstract ResourceType getType();

    /**
     * @return the string representation of this resource in the cli.
     */
    public abstract String toStringCli();

    @Override
    public abstract String toString();
    
}
