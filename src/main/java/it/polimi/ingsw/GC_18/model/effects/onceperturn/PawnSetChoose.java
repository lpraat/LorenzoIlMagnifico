package it.polimi.ingsw.GC_18.model.effects.onceperturn;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;

/**
 * This class represents an once per turn effect that sets the value of a pawn to a specific value.
 */
public class PawnSetChoose implements OncePerTurnEffect, Serializable, Blocking {
    
    private static final long serialVersionUID = -8081996301902886673L;
    
    private int value;
    private boolean activated;

    /**
     * Creates a new PawnSetChoose effect.
     * @param value the value to set.
     */
    public PawnSetChoose(int value) {
        this.value = value;
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    @Override
    public void apply(Player player) {
        String response = block(player, WaitingCommand.PAWN_CHOOSE);
        switch (response) {
            case "BLACK":
                player.getBlackPawn().setValue(value);
                break;
            case "WHITE":
                player.getWhitePawn().setValue(value);
                break;
            case "ORANGE":
                player.getOrangePawn().setValue(value);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isActivable() {
        return !activated;
    }

    @Override
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "Sets the value of a pawn you decide to " + value;
    }
    
}
