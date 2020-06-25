package it.polimi.ingsw.GC_18.model.actions;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;

/**
 * Tests the normal action.
 */
public class ActionTest {

    @Test
    public void testActions() {
        List<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("a", PlayerColor.YELLOW);
        playerArrayList.add(player1);
        Player player2 = new Player("b", PlayerColor.RED);
        playerArrayList.add(player2);
        Player player3 = new Player("c", PlayerColor.BLUE);
        playerArrayList.add(player3);
        Player player4 = new Player("e", PlayerColor.GREEN);
        playerArrayList.add(player4);
        Player player5 = new Player("e", PlayerColor.VIOLET);
        playerArrayList.add(player5);
        Board board = new Board(playerArrayList);


        // test all normal actions
        Action action1 = new Action(player1.getBlackPawn(), board.getMarket().getCoinSpot(), 1);
        assertTrue(action1.check());
        action1.run();
        // pawn already placed
        Action action2 = new Action(player1.getBlackPawn(), board.getMarket().getServantSpot(), 1);
        assertTrue(!action2.check());
        // place is occupied
        Action action3 = new Action(player2.getBlackPawn(), board.getMarket().getCoinSpot(), 1);
        assertTrue(!action3.check());
        // test harvest-production action
        Action action6 = new Action(player1.getBlackPawn(), board.getHarvestSpace().getHarvestArea(), 1);
        assertTrue(!action6.check());
        // larger areas are activated because 3 player mode
        // refused because action value=0 not enough
        Action action7 = new Action(player3.getBlackPawn(), board.getHarvestSpace().getLargeHarvestArea(), 3);
        assertTrue(!action7.check());
        // ok
        Action action8 = new Action(player3.getBlackPawn(), board.getProductionSpace().getLargeProductionArea(), 4);
        assertTrue(action8.check());
        // test tower actions
        // no military spot - only in 4 player mode
        assertTrue(board.getMarket().getMilitarySpot() != null);
        // no council spot - only in 4 player mode
        assertTrue(board.getMarket().getCouncilSpot() != null);


        Action action9 = new Action(player4.getBlackPawn(), board.getFightSpace().getPlayerSpot(player4), 0);
        assertTrue(action9.check());

        board.getFightSpace().addBonus(0);
        board.getFightSpace().addBonus(1);
        board.getFightSpace().addBonus(2);
        board.getFightSpace().addBonus(3);
        board.getFightSpace().addBonus(4);
        board.getFightSpace().addBonus(5);

        assertTrue(player4.getMoney()==0);
        assertTrue(player4.getServants() == 0);
        assertTrue(player4.getWoods() == 0);
        assertTrue(player4.getStones() == 0);
        assertTrue(player4.getFaithPoints() == 0);
        assertTrue(player4.getVictoryPoints() == 0);


        assertTrue("money".equals(board.getFightSpace().getPriceType(0)));
        assertTrue("servants".equals(board.getFightSpace().getPriceType(1)));
        assertTrue("woods".equals(board.getFightSpace().getPriceType(2)));
        assertTrue("stones".equals(board.getFightSpace().getPriceType(3)));
        assertTrue("faith".equals(board.getFightSpace().getPriceType(4)));
        assertTrue("victory".equals(board.getFightSpace().getPriceType(5)));
    }

}