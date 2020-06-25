package it.polimi.ingsw.GC_18.model.effects.dynamic;

import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.GC_18.model.actions.TowerAction;
import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;

/**
 * Tests the FloorChange effect.
 */
public class FloorChangeTest extends EffectTest {

    @Test
    public void testApply() {

        // floorChange gives a value added of 1 for placing the pawn and a discount of 1 money for picking up the card
        FloorChange floorChange = new FloorChange(TowerColor.BLUE, 1, getResources());
        Player player = getPlayer();
        player.addMoney(1, null); // since Warlord costs 2 money
        player.addObserver(floorChange);
        Board board = getBoard();
        board.getCharacterTower().getFirstFloor().setCard(getWarlord());
        TowerAction towerAction = new TowerAction(player.getBlackPawn(), board.getCharacterTower().getFirstFloor(), 0);
        assertTrue(towerAction.check());
    }
}