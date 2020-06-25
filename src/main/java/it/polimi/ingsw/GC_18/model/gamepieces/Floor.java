package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents a tower floor.
 * Every floor can keep a DevelopmentCard.
 */
public class Floor<T extends DevelopmentCard> extends ActionPlace implements Serializable {

    private static final long serialVersionUID = -8969412293885203725L;


    private T card;
    private Tower<T> tower;

    /**
     * Creates a new floor.
     * @param placeBonus the resources bonus of the floor.
     * @param placeValue the value needed for placing in this floor.
     * @param tower the floor's tower.
     */
    public Floor(Resources placeBonus, int placeValue, Tower<T> tower) {
        super(placeBonus, placeValue);
        this.tower = tower;
    }

    /**
     * @return the floor's tower.
     */
    public Tower<T> getTower() {
        return tower;
    }

    /**
     * @return the floor's card.
     */
    public T getCard() {
        return card;
    }

    /**
     * @param card the card to be set.
     */
    public void setCard(T card) {
        this.card = card;
    }

    /**
     * @return the name of the place.
     */
    @Override
    public PlaceName getPlaceName() {
        return PlaceName.FLOOR;
    }
    
}
