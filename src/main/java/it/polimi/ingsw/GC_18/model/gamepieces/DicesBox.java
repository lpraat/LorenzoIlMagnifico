package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Dice;
import it.polimi.ingsw.GC_18.model.DiceColor;

/**
 * This class represents the boxes in board for the dices.
 */
public class DicesBox implements Serializable {
    
    private static final long serialVersionUID = -2259321279477845747L;
    
    private Dice blackDice;
    private Dice whiteDice;
    private Dice orangeDice;

    /**
     * Create a new box by adding to it the three dices.
     * @param blackDice the black dice.
     * @param whiteDice the white dice.
     * @param orangeDice the orange dice.
     */
    public DicesBox(Dice blackDice, Dice whiteDice, Dice orangeDice) {
        this.blackDice = blackDice;
        this.whiteDice = whiteDice;
        this.orangeDice = orangeDice;
    }

    /**
     * Returns the dice given the color.
     * @param diceColor the color of the dice.
     * @return the dice whose color is diceColor.
     */
    public Dice getDice(DiceColor diceColor) {
        switch (diceColor) {
        case BLACK:
            return blackDice;
        case ORANGE:
            return orangeDice;
        case WHITE:
            return whiteDice;
        default:
            return null;
        }
    }

    /**
     * Rolls the dices in the box.
     */
    public void rollDices() {
        blackDice.roll();
        whiteDice.roll();
        orangeDice.roll();
    }
    
}
