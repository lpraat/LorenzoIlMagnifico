package it.polimi.ingsw.GC_18.model.effects.decorators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;

/**
 * Tests the harvest effect decorator.
 */
public class HarvestTest extends EffectTest{
    @Test
    public void testApply() {
        Player player = getPlayer();

        Harvest harvest = new Harvest(3, new Bonus(getResources()));
        if (harvest.isActivable(3)) {
            harvest.apply(player);
        }
        assertEquals(1, player.getWoods());
        assertEquals(1, player.getStones());
        assertEquals(1, player.getServants());
        assertEquals(1, player.getMoney());
        assertEquals(1, player.getMilitaryPoints());
        assertEquals(1, player.getFaithPoints());
        assertEquals(1, player.getVictoryPoints());
    }
    @Test
    public void testApplyFail() {
        Player player = getPlayer();

        Harvest harvest = new Harvest(3, new Bonus(getResources()));
        if (harvest.isActivable(1)) {
            harvest.apply(player);
        }
        assertEquals(0, player.getWoods());
        assertEquals(0,  player.getStones());
        assertEquals(0, player.getServants());
        assertEquals(0, player.getMoney());
        assertEquals(0, player.getMilitaryPoints());
        assertEquals(0, player.getFaithPoints());
        assertEquals(0, player.getVictoryPoints());
    }
}