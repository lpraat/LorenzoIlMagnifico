package it.polimi.ingsw.GC_18.model.actions;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.resources.*;
import it.polimi.ingsw.GC_18.utils.CardLoader;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the tower action.
 */
public class TowerActionTest {

    @Test
    public void testTowerActions() {
        List<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("a", PlayerColor.YELLOW);
        playerArrayList.add(player1);
        Player player2 = new Player("b", PlayerColor.RED);
        playerArrayList.add(player2);
        Player player3 = new Player("c", PlayerColor.BLUE);
        playerArrayList.add(player3);
        Board board = new Board(playerArrayList);
        ArrayList<Character> characters1 = CardLoader.characterLoader(CardLoader.loadCards(0, "characterCards"));

        board.getCharacterTower().getFirstFloor().setCard(characters1.get(0)); // Warlord
        board.getCharacterTower().getSecondFloor().setCard(characters1.get(1)); // Stonemason


        playerArrayList.forEach( player -> {
                    player.getBlackPawn().setValue(6);
                    player.getOrangePawn().setValue(6);
                    player.getWhitePawn().setValue(6);
                    player.getNeutralPawn().setValue(0);
                });

        TowerAction towerAction = new TowerAction(player1.getBlackPawn(), board.getTerritoryTower().getFirstFloor(), 0);
        assertTrue(!towerAction.check()); // no card in this floor

        TowerAction towerAction1 = new TowerAction(player1.getBlackPawn(), board.getCharacterTower().getFirstFloor(), 0);
        assertTrue(!towerAction1.check()); // can't buy the card

        player1.setMoney(2);
        TowerAction towerAction2 = new TowerAction(player1.getBlackPawn(), board.getCharacterTower().getFirstFloor(), 0);
        assertTrue(towerAction2.check()); // can buy the card
        towerAction2.run();
        TowerAction towerAction3 = new TowerAction(player1.getOrangePawn(), board.getCharacterTower().getSecondFloor(), 0);
        assertTrue(!towerAction3.check()); // there is already a coloured pawn of player1


        player2.setMoney(4);
        TowerAction towerAction4 = new TowerAction(player2.getOrangePawn(), board.getCharacterTower().getSecondFloor(), 0);
        assertTrue(!towerAction4.check()); // tower already occupied, don't have enough money



        ArrayList<Territory> territories1 = CardLoader.territoryLoader(CardLoader.loadCards(0, "territoryCards"));

        for (int i = 0; i < 2; i++) {
            player3.getPersonalBoard().getTerritories().add(territories1.get(i));
        }

        board.getTerritoryTower().getFirstFloor().setCard(territories1.get(0));
        TowerAction towerAction5 = new TowerAction(player3.getBlackPawn(), board.getTerritoryTower().getFirstFloor(), 0);
        assertTrue(!towerAction5.check()); // not enough military for picking this territory

        player3.setMilitaryPoints(3);
        TowerAction towerAction6 = new TowerAction(player3.getBlackPawn(), board.getTerritoryTower().getFirstFloor(), 0);
        assertTrue(towerAction6.check()); // enough military now

        for (int i = 0; i < 4; i++) {
            player3.getPersonalBoard().getTerritories().add(territories1.get(i));
        }

        TowerAction towerAction7 = new TowerAction(player3.getBlackPawn(), board.getTerritoryTower().getFirstFloor(), 0);
        assertTrue(!towerAction7.check()); // has already 6 territories
    }


    /**
     * Tests the discount is applied only if needed and not added to the player.
     */
    @Test
    public void testIncreaseResources() {
        List<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("a", PlayerColor.YELLOW);
        playerArrayList.add(player1);
        Board board = new Board(playerArrayList);
        ArrayList<Character> characters1 = CardLoader.characterLoader(CardLoader.loadCards(0, "characterCards"));

        board.getCharacterTower().getFirstFloor().setCard(characters1.get(0)); // Warlord
        TowerAction towerAction = new TowerAction(player1.getBlackPawn(), board.getCharacterTower().getFirstFloor(), 0);
        towerAction.increaseResources(new Resources(new Woods(2000), new Stones(0), new Servants(0), new Money(5000), new MilitaryPoints(0), new FaithPoints(0),
                new CouncilPrivileges(0), new VictoryPoints(300)));


        assertTrue(towerAction.player.getWoods() == 0);
        assertTrue(towerAction.player.getVictoryPoints() == 0);

        // Warlord costs 2 money, the player has a discount of 5k money so the player in the action must have 2 money.
        assertTrue(towerAction.player.getMoney() == 2);


    }

}
