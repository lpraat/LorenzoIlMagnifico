package it.polimi.ingsw.GC_18.model.cards;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.GC_18.utils.CardLoader;

import static org.junit.Assert.assertEquals;

/**
 * Tests the territories loading.
 */
public class TerritoryTest {
    @Test
    public void testCards() {
        ArrayList<Territory> territories1 = CardLoader.territoryLoader(CardLoader.loadCards(0, "territoryCards"));
        ArrayList<Territory> territories2 = CardLoader.territoryLoader(CardLoader.loadCards(1, "territoryCards"));
        ArrayList<Territory> territories3 = CardLoader.territoryLoader(CardLoader.loadCards(2, "territoryCards"));

        assertEquals(8, territories1.size());
        assertEquals(8, territories2.size());
        assertEquals(8, territories3.size());

    }
}