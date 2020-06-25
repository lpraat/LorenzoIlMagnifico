package it.polimi.ingsw.GC_18.model.notifiers;

import it.polimi.ingsw.GC_18.model.actions.Action;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;

/**
 * This class represents a notify for a pawn placement.
 */
public class NotifyPlacePawn {
    private Action action;
    private Pawn pawn;

    /**
     * Creates a new place pawn notify.
     * @param pawn the pawn placed.
     * @param action the action for which the pawn is placed.
     */
    public NotifyPlacePawn(Pawn pawn, Action action) {
        this.pawn = pawn;
        this.action = action;
    }

    /**
     * @return the pawn of the notify.
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * @return the action of the notify.
     */
    public Action getAction() {
        return action;
    }
}
