package it.polimi.ingsw.GC_18.model.effects.endgame;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;

/**
 * Tests the EndGameVictoryMalus effect.
 */
public class EndGameVictoryMalusTest extends EffectTest{

    @Test
    public void testApply() {
        EndGameVictoryMalus endGameVictoryMalus = new EndGameVictoryMalus(getResources());
        Player player = getPlayer();


        endGameVictoryMalus.apply(player);
        assertTrue(player.getVictoryPoints()==0);
    }
}