package it.polimi.ingsw.GC_18.model.cards;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.GC_18.utils.CardLoader;

/**
 * Tests the building loading.
 */
public class BuildingTest {

    @Test
    public void testCards() {
        ArrayList<Building> buildings1 = CardLoader.buildingLoader(CardLoader.loadCards(0, "buildingCards"));
        ArrayList<Building> buildings2 = CardLoader.buildingLoader(CardLoader.loadCards(1, "buildingCards"));
        ArrayList<Building> buildings3 = CardLoader.buildingLoader(CardLoader.loadCards(2, "buildingCards"));
        assertEquals(8, buildings1.size());
        assertEquals(8, buildings2.size());
        assertEquals(8, buildings3.size());

    }

}