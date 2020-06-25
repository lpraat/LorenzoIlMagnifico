package it.polimi.ingsw.GC_18.model;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a dice.
 */
public class Dice implements Serializable {

    private static final long serialVersionUID = -8910697814380120493L;
    
    private DiceColor color;
    private int value;

    /**
     * Creates a new dice.
     * @param color the dice's color.
     */
    Dice(DiceColor color) {
        this.color = color;
    }

    /**
     * @return the dice's color.
     */
    public DiceColor getColor() {
        return color;
    }

    /**
     * @return the dice's value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Roll the dice. Sets a value from 1 to 6.
     */
    public void roll() {
        this.value = ThreadLocalRandom.current().nextInt(1, 7);
    }

}