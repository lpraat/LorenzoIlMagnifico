package it.polimi.ingsw.GC_18.model.cards;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.GC_18.utils.CardLoader;

/**
 * Tests the leader loading.
 */
public class LeaderTest {

    @Test
    public void testCards() {
        ArrayList<Leader> leaderCards = CardLoader.leaderLoader(CardLoader.loadCards(-1, "leaderCards"));
        assertEquals(20, leaderCards.size());
    }

}