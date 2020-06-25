package it.polimi.ingsw.GC_18.model.cards;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;
import it.polimi.ingsw.GC_18.model.actions.Action;
import it.polimi.ingsw.GC_18.utils.CardLoader;

/**
 * Tests the excomunication tiles loading and their picks.
 */
public class ExcomunicationTileTest {

    @Test
    public void testCards() {
        List<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("a", PlayerColor.YELLOW);
        playerArrayList.add(player1);
        Player player2 = new Player("b", PlayerColor.RED);
        playerArrayList.add(player2);
        Player player3 = new Player("c", PlayerColor.BLUE);
        playerArrayList.add(player3);
        Board board = new Board(playerArrayList);

        ArrayList<ExcomunicationTile> excomunicationTilesFirst = CardLoader.excomunicationLoader(CardLoader.loadCards(0, "excomunicationTiles"));
        ArrayList<ExcomunicationTile> excomunicationTilesSecond = CardLoader.excomunicationLoader(CardLoader.loadCards(1, "excomunicationTiles"));
        @SuppressWarnings("unused")
        ArrayList<ExcomunicationTile> excomunicationTilesThird = CardLoader.excomunicationLoader(CardLoader.loadCards(2, "excomunicationTiles"));

        player1.pickCard(excomunicationTilesFirst.get(3));
        player1.pickCard(excomunicationTilesSecond.get(4)); // test 8
        // malus of -4
        player1.pickCard(excomunicationTilesFirst.get(4));
        // he needs to spend 7 servants because he has the -3 malus from the tile + the -3 malus of the largest area
        Action action1 = new Action(player1.getOrangePawn(), board.getHarvestSpace().getLargeHarvestArea(), 7);
        assertTrue(action1.check());
        action1.run();

        player1.pickCard(excomunicationTilesFirst.get(5));
        // malus -3
        Action action2 = new Action(player1.getWhitePawn(), board.getProductionSpace().getProductionArea(), 4);
        assertTrue(action2.check());
        action2.run();
        // test 7
        player2.pickCard(excomunicationTilesFirst.get(6));
        // malus of -1 from dice
        Action action3 = new Action(player2.getOrangePawn(), board.getMarket().getCoinSpot(), 1);
        assertTrue(!action3.check());

        player3.pickCard(excomunicationTilesSecond.get(1)); // test 12
        player3.pickCard(excomunicationTilesSecond.get(2)); // test 13
    }

}