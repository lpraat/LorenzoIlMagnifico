package it.polimi.ingsw.GC_18.model.effects.decorators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;

/**
 * Tests the once per turn effect decorator.
 */
public class OncePerTurnTest extends EffectTest {

    @Test
    public void testApply() {
        Player player = getPlayer();

        OncePerTurn oncePerTurn = new OncePerTurn(new Bonus(getResources()));
        oncePerTurn.apply(player);
        oncePerTurn.setActivated(true);
        assertEquals(1, player.getWoods());
        assertEquals(1, player.getStones());
        assertEquals(1, player.getServants());
        assertEquals(1, player.getMoney());
        assertEquals(1, player.getMilitaryPoints());
        assertEquals(1, player.getFaithPoints());
        assertEquals(1, player.getVictoryPoints());
        assertEquals(false, oncePerTurn.isActivable());
    }

}