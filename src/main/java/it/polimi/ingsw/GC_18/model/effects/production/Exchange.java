package it.polimi.ingsw.GC_18.model.effects.production;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.server.controller.Blocking;

/**
 * This class represents a production effect that let the player exchange resources.
 */
public class Exchange extends ProductionExchange implements Serializable, Blocking {
    
    private static final long serialVersionUID = -5911959642923200531L;
    
    private Resources in;
    private Resources out;

    /**
     * Creates a new Exchange effect.
     * @param in the resources given by the player.
     * @param out the resources the player gets.
     * @param activationValue the activation value of the production action.
     */
    public Exchange(Resources in, Resources out, int activationValue) {
        super(activationValue);
        this.in = in;
        this.out = out;
    }

    /**
     * @param player the player.
     * @return true if the player has enough resources to make the exchange.
     */
    private boolean isExchangePossibile(Player player) {
        return Resources.compare(player, in);
    }

    @Override
    public void apply(Player player) {
        if (isExchangePossibile(player)) {
            if (!ask(player, this))
                return;
            in.subtractResources(player);
            out.addResources(player, source);
        }
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    @Override
    public String toString() {
        return "You can give in the following resources \n" + in.toString() + "and get \n" + out.toString();
    }
    
}