package it.polimi.ingsw.GC_18.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.GC_18.model.cards.Card;

/**
 * This class represents a card deck.
 */
public class Deck<T extends Card> implements Serializable {

    private static final long serialVersionUID = -4585014721307306246L;
    
    private ArrayList<T> cards;

    /**
     * Creates a new deck.
     */
    Deck() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a card to the deck
     * @param card the card to be added.
     */
    void addCard(T card) {
        cards.add(card);
    }

    /**
     * Shuffles the deck.
     */
    void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * @return the card picked. The card is removed from the deck.
     */
    T pickCard() {
        return cards.remove(0);
    }

    /**
     * @return the cards of the deck.
     */
    public List<T> getCards() {
        return cards;
    }
    
}
