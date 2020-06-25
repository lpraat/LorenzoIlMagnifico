package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;

/**
 * This abstract class represents a Card in the game.
 */
public abstract class Card implements Serializable {

    private static final long serialVersionUID = 410281315619640317L;
    static final String NAME = "name";

    String name;

    /**
     * @return the name of the Card
     */
    public String getName() {
        return name;

    }

}