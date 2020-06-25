package it.polimi.ingsw.GC_18.model.effects.immediate;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.server.controller.Parser;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;
import it.polimi.ingsw.GC_18.model.actions.ExtraAction;
import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;

/**
 * This class represents an immediate effect that gives the player a possibility to perform
 * an extra production action without placing the pawn.
 */
public class ExtraProduction implements ImmediateEffect, Blocking, Serializable {
    
    private static final long serialVersionUID = 1544510892256266916L;
    
    private int value;
    private boolean changeable;

    /**
     * Creates a new ExtraProductio neffect.
     * @param value the value of the extra action.
     * @param changeable true if the value of the extra harvest is changeable with card effects.
     */
    public ExtraProduction(int value, boolean changeable) {
        this.value = value;
        this.changeable = changeable;
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    @Override
    public void apply(Player player) {
        if (!ask(player, this))
            return;
        String response = block(player, WaitingCommand.EXTRA_PRODUCTION);
        player.getController().getGame().getGameController().notifyPlayer(player, "Starting extra production");
        ExtraAction extraAction = new ExtraAction(player, Parser.parseActionPlace(player, "PRODUCTION_AREA"), Integer.parseInt(response), value, changeable);
        extraAction.run();
    }

    @Override
    public String toString() {
        return "Gives the possibility of a production action with a starting value of " + value;
    }
    
}