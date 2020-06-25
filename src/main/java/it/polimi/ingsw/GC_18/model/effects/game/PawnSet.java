package it.polimi.ingsw.GC_18.model.effects.game;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.GameEffect;

/**
 * This class represents a game effect that sets the value to one or more pawns.
 */
public class PawnSet implements GameEffect, Serializable {
   
    private static final long serialVersionUID = 4234712140557423106L;
    
    private List<DiceColor> diceColors;
    private int value;

    /**
     * @param value the value to be set
     * @param diceColors the colors of the pawns to set
     */
    public PawnSet(int value, List<DiceColor> diceColors) {
        this.value = value;
        this.diceColors = diceColors;
    }

    @Override
    public void apply(Player player) {
        for (DiceColor diceColor : diceColors) {
            if (diceColor.equals(DiceColor.ORANGE)) {
                player.getOrangePawn().setValue(this.value);
            }
            if (diceColor.equals(DiceColor.BLACK)) {
                player.getBlackPawn().setValue(this.value);
            }
            if (diceColor.equals(DiceColor.WHITE)) {
                player.getWhitePawn().setValue(this.value);
            }
        }
    }

    /**
     * @return the value of the effect.
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        diceColors.forEach(diceColor -> stringBuilder.append(diceColor.name()).append(" "));
        return "This sets to " + value + " the value of the following pawns " + stringBuilder.toString();
    }
    
}
