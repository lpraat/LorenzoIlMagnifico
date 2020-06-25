package it.polimi.ingsw.GC_18.model.effects.production;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;

/**
 * This class represents a production effect that let the player exchange resources
 * by choosing between two types of exchange.
 */
public class ExchangeOr extends ProductionExchange implements Blocking, Serializable {

    private static final long serialVersionUID = -6405287236601158693L;

    private Resources in1;
    private Resources out1;
    private Resources in2;
    private Resources out2;

    /**
     * Creates a new ExchangeOr effect.
     * @param in1 the first option resources given by the player.
     * @param out1 the first option resources the player gets.
     * @param in2 the second option resources given by the player.
     * @param out2 the second option resources the player gets.
     * @param activationValue the activation value of the production action.
     */
    public ExchangeOr(Resources in1, Resources out1, Resources in2, Resources out2, int activationValue) {
        super(activationValue);
        this.in1 = in1;
        this.out1 = out1;
        this.in2 = in2;
        this.out2 = out2;
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    /**
     * @param optionNumber the option chosen.
     * @param player the player.
     * @return true if the player can make the exchange chosen.
     */
    boolean isExchangePossible(Player player, int optionNumber) {
        if ((optionNumber == 1 && Resources.compare(player, in1)) || (optionNumber == 2 && Resources.compare(player, in2))) {
            return true;
        }
        return false;
    }

    /**
     * Makes the exchange chosen.
     * @param player the player.
     * @param description the option chosen.
     */
    void doAction(Player player, String description) {
        if ("1".equals(description) && isExchangePossible(player, 1)) {
            in1.subtractResources(player);
            out1.addResources(player, source);
        } else if ("2".equals(description) && isExchangePossible(player, 2)) {
            in2.subtractResources(player);
            out2.addResources(player, source);
        }
    }

    @Override
    public void apply(Player player) {
        if (!ask(player, this))
            return;
        if ((isExchangePossible(player, 1)) && isExchangePossible(player, 2)) {
            doAction(player, block(player, WaitingCommand.EXCHANGE_OR));
        } else if (isExchangePossible(player, 1)) {
            doAction(player, "1");
        } else  if (isExchangePossible(player, 2)) {
            doAction(player, "2");
        }
    }

    @Override
    public String toString() {
        return "You can give in the following resources \n" + in1.toString() + " or " + in2.toString() + " \n and get \n" + out1.toString() + " or " + out2.toString();
    }

}