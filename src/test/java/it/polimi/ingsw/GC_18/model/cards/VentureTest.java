package it.polimi.ingsw.GC_18.model.cards;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.GC_18.utils.CardLoader;

/**
 * Tests the venture loading.
 */
public class VentureTest {
    @Test
    public void testCards() {
        ArrayList<Venture> ventures1 = CardLoader.ventureLoader(CardLoader.loadCards(0, "ventureCards"));
        ArrayList<Venture> ventures2 = CardLoader.ventureLoader(CardLoader.loadCards(1, "ventureCards"));
        ArrayList<Venture> ventures3 = CardLoader.ventureLoader(CardLoader.loadCards(2, "ventureCards"));
        assertEquals(8, ventures1.size());
        assertEquals(8, ventures2.size());
        assertEquals(8, ventures3.size());
    }

}