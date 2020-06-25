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
 * an extra harvest action without placing the pawn.
 */
public class ExtraHarvest implements ImmediateEffect, Blocking, Serializable {
    
    private static final long serialVersionUID = -895135916894982567L;
    
    private int value;
    private boolean changeable;

    /**
     * Creates a new ExtraHarvest effect.
     * @param value the value of the extra action.
     * @param changeable true if the value of the extra harvest is changeable with card effects.
     */
    public ExtraHarvest(int value, boolean changeable) {
        this.value = value;
        this.changeable = changeable;
    }



    @Override
    public void apply(Player player) {
        if (!ask(player, this))
            return;
        String response = block(player, WaitingCommand.EXTRA_HARVEST);
        player.getController().getGame().getGameController().notifyPlayer(player, "Starting extra harvest");
        ExtraAction extraAction = new ExtraAction(player, Parser.parseActionPlace(player, "HARVEST_AREA"), Integer.parseInt(response), value, changeable);
        extraAction.run();
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    @Override
    public String toString() {
        return "Gives the possibility of an harvest action with a starting value of " + value;

    }
}