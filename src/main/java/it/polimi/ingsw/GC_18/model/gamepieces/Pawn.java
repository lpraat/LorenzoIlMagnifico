package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.Player;

/**
 * This class represents a pawn in the game, i.e. the family member.
 */
public class Pawn implements Serializable {

    private static final long serialVersionUID = 3553169297502404097L;

    private DiceColor color;
    private boolean placed;
    private Player player;
    private int value;

    /**
     * Creates a new pawn for the player which is associated to a dice color.
     * @param diceColor the dice color.
     * @param player the player of the pawn.
     */
    public Pawn(DiceColor diceColor, Player player) {
        this.color = diceColor;
        this.player = player;
    }

    /**
     * @return the value of the pawn.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the pawn value.
     * @param value the value to be sed.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the color of the pawn.
     */
    public DiceColor getColor() {
        return color;
    }

    /**
     * @return true if the pawn is placed, false otherwise.
     */
    public boolean isPlaced() {
        return placed;
    }

    /**
     * Sets the pawn state to placed.
     * @param placed boolean indicating if the pawn is placed.
     */
    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    /**
     * @return the owner player of the pawn.
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return color.name();
    }

}
