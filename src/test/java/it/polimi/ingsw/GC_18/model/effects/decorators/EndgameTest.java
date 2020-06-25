package it.polimi.ingsw.GC_18.model.effects.decorators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;

/**
 * Tests the end game effect decorator.
 */
public class EndgameTest extends EffectTest{
    @Test
    public void testApply() {
        Player player = getPlayer();

        Endgame endgame = new Endgame(new Bonus(getResources()));
        endgame.apply(player);
        assertEquals(1, player.getWoods());
        assertEquals(1, player.getStones());
        assertEquals(1, player.getServants());
        assertEquals(1, player.getMoney());
        assertEquals(1, player.getMilitaryPoints());
        assertEquals(1, player.getFaithPoints());
        assertEquals(1, player.getVictoryPoints());
    }
}