package it.polimi.ingsw.GC_18.model.actions;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.resources.*;
import it.polimi.ingsw.GC_18.utils.CardLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


/**
 * Tests the extra tower action.
 */
public class ExtraTowerActionTest {

    @Test
    public void testExtraTowerAction() {
        List<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("a", PlayerColor.YELLOW);
        playerArrayList.add(player1);
        Player player2 = new Player("b", PlayerColor.RED);
        playerArrayList.add(player2);
        Board board = new Board(playerArrayList);
        ArrayList<Character> characters1 = CardLoader.characterLoader(CardLoader.loadCards(0, "characterCards"));

        board.getCharacterTower().getFirstFloor().setCard(characters1.get(0));
        board.getCharacterTower().getSecondFloor().setCard(characters1.get(1));


        // extra action with discount of 2 money
        ExtraTowerAction extraTowerAction1 = new ExtraTowerAction(player1, board.getCharacterTower().getFirstFloor(), 0, 1,
                new Resources(new Woods(0), new Stones(0), new Servants(0), new Money(2), new MilitaryPoints(0), new FaithPoints(0),
                        new CouncilPrivileges(0), new VictoryPoints(0)));

        assertTrue(extraTowerAction1.check());
        extraTowerAction1.run();

        // picked the card because of the discount
        assertTrue(player1.getPersonalBoard().getCharacters().get(0).getName().endsWith("Warlord"));
    }
}

