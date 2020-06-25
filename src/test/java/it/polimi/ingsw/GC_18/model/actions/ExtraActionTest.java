package it.polimi.ingsw.GC_18.model.actions;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.GameMode;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;
import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.model.gamepieces.BonusTile;
import it.polimi.ingsw.GC_18.utils.CardLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Tests the extra harvest and production action.
 */
public class ExtraActionTest {

    @Test
    public void testExtraAction() {
        List<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("a", PlayerColor.YELLOW);
        playerArrayList.add(player1);
        Player player2 = new Player("b", PlayerColor.RED);
        playerArrayList.add(player2);
        Board board = new Board(playerArrayList);

        player1.setServants(3);
        player1.setBonusTile(new BonusTile(GameMode.ADVANCED, 0));
        ExtraAction extraAction1 = new ExtraAction(player1, board.getHarvestSpace().getHarvestArea(), 3, 1, true);
        extraAction1.run();
        assertTrue(player1.getServants()==1); // servants of the bonus tile


        player2.setBonusTile(new BonusTile(GameMode.ADVANCED, 0));
        ArrayList<Building> buildings1 = CardLoader.buildingLoader(CardLoader.loadCards(0, "buildingCards"));
        buildings1.stream().filter(b -> b.getName().equals("Mint")).collect(Collectors.toList()).get(0).add(player2); // adds Mint to player2
        ExtraAction extraAction2 = new ExtraAction(player2, board.getProductionSpace().getProductionArea(), 0, 6, true);
        extraAction2.run();

        assertEquals(3, player2.getMoney()); // the player2 has 3 money, 1 from the permanent effect plus 2 from the bonus tile
    }

}