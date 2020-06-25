package it.polimi.ingsw.GC_18.model.cards;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.GC_18.utils.CardLoader;

/**
 * Tests the characters loading.
 */
public class CharacterTest {

    @Test
    public void testCards() {
        ArrayList<Character> characters1 = CardLoader.characterLoader(CardLoader.loadCards(0, "characterCards"));
        ArrayList<Character> characters2 = CardLoader.characterLoader(CardLoader.loadCards(1, "characterCards"));
        ArrayList<Character> characters3 = CardLoader.characterLoader(CardLoader.loadCards(2, "characterCards"));
        assertEquals(8, characters1.size());
        assertEquals(8, characters2.size());
        assertEquals(8, characters3.size());
    }

}