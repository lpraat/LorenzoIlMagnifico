package it.polimi.ingsw.GC_18.model.effects.dynamic;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.effects.DynamicEffect;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyPlacePawn;

/**
 * This class represents a dynamic effect that adds a bonus to the pawn value for an Action.
 */
public class PawnValueChange implements DynamicEffect, Serializable {
   
    private static final long serialVersionUID = 4648523272049000457L;
    
    private int value;
    private List<DiceColor> diceColors;

    /**
     * Creates a new PawnValueChange effect.
     * @param value the value of the change.
     * @param diceColors the dice colors that get the change.
     */
    public PawnValueChange(int value, List<DiceColor> diceColors) {
        this.value = value;
        this.diceColors = diceColors;
    }

    /**
     * Using a NotifyPlacePawn applies the effect.
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            NotifyPlacePawn notifyPlacePawn = (NotifyPlacePawn) arg;
            Pawn pawn = notifyPlacePawn.getPawn();
            for (DiceColor diceColor : diceColors) {
                if (pawn.getColor().equals(diceColor)) {
                    notifyPlacePawn.getAction().changeActionValue(value);
                }
            }
        } catch (ClassCastException e) {
            ModelLogger.getInstance().log(Level.INFO, "Notify thrown", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        diceColors.forEach(diceColor -> stringBuilder.append(diceColor.name()).append(" "));
        return "Adds " + value + " value to " + stringBuilder.toString() + "dices";
    }
    
}
