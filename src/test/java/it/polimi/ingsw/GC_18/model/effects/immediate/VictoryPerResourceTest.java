package it.polimi.ingsw.GC_18.model.effects.immediate;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;

/**
 * Tests the VictoryPerResource effect.
 */
public class VictoryPerResourceTest extends EffectTest {

    @Test
    public void testApply() {
        Player player = getPlayer();
        getResources().addResources(player, null);
        VictoryPerResource victoryPerResource = new VictoryPerResource(getResources());
        victoryPerResource.apply(player);
        assertTrue(player.getVictoryPoints() == 7);
    }


}