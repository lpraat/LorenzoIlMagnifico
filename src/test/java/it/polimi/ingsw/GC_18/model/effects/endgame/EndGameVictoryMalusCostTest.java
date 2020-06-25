package it.polimi.ingsw.GC_18.model.effects.endgame;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;

/**
 * Tests the EndGameVictoryMalusCost effect.
 */
public class EndGameVictoryMalusCostTest extends EffectTest {

    @Test
    public void testApply() {
        Player player = getPlayer();
        player.setVictoryPoints(10);
        player.getPersonalBoard().getCharacters().add(getWarlord());
        player.getPersonalBoard().getTerritories().add(getMonastery());
        player.getPersonalBoard().getBuildings().add(getTheater());
        player.getPersonalBoard().getVentures().add(getHiringRecruits());


        EndGameVictoryMalusCost endGameVictoryMalusCost = new EndGameVictoryMalusCost(TowerColor.YELLOW);
        endGameVictoryMalusCost.apply(player);
        assertTrue(player.getVictoryPoints() == 8);

        EndGameVictoryMalusCost endGameVictoryMalusCost1 = new EndGameVictoryMalusCost(TowerColor.BLUE);
        endGameVictoryMalusCost1.apply(player);
        assertTrue(player.getVictoryPoints() == 8);

        EndGameVictoryMalusCost endGameVictoryMalusCost2 = new EndGameVictoryMalusCost(TowerColor.GREEN);
        endGameVictoryMalusCost2.apply(player);
        assertTrue(player.getVictoryPoints() == 8);

        EndGameVictoryMalusCost endGameVictoryMalusCost3 = new EndGameVictoryMalusCost(TowerColor.PURPLE);
        endGameVictoryMalusCost3.apply(player);
        assertTrue(player.getVictoryPoints() == 8);
    }

}