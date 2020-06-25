package it.polimi.ingsw.GC_18.model.effects.decorators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;

/**
 * Tests the double immediate effect decorator.
 */
public class DoubleEffectImmediateTest extends EffectTest{

    @Test
    public void apply() {
        Player player = getPlayer();

        DoubleEffectImmediate doubleEffectImmediate = new DoubleEffectImmediate(
                new Bonus(getResources()), new Bonus(getResources())
        );
        doubleEffectImmediate.apply(player);
        assertEquals(2, player.getWoods());
        assertEquals(2, player.getStones());
        assertEquals(2, player.getServants());
        assertEquals(2, player.getMoney());
        assertEquals(2, player.getMilitaryPoints());
        assertEquals(2, player.getFaithPoints());
        assertEquals(2, player.getVictoryPoints());
    }
}