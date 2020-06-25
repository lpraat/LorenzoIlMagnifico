package it.polimi.ingsw.GC_18.model.effects.decorators;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;
import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.cards.Venture;
import it.polimi.ingsw.GC_18.model.resources.CouncilPrivileges;
import it.polimi.ingsw.GC_18.model.resources.FaithPoints;
import it.polimi.ingsw.GC_18.model.resources.MilitaryPoints;
import it.polimi.ingsw.GC_18.model.resources.Money;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.model.resources.Servants;
import it.polimi.ingsw.GC_18.model.resources.Stones;
import it.polimi.ingsw.GC_18.model.resources.VictoryPoints;
import it.polimi.ingsw.GC_18.model.resources.Woods;
import it.polimi.ingsw.GC_18.utils.CardLoader;

/**
 * This class represents a set of method used by other effect tests.
 */
public class EffectTest {

    private ArrayList<Character> getCharacters() {
        return CardLoader.characterLoader(CardLoader.loadCards(0, "characterCards"));
    }

    private ArrayList<Building> getBuildings() {
        return CardLoader.buildingLoader(CardLoader.loadCards(0, "buildingCards"));
    }
    private ArrayList<Venture> getVentures() {
        return CardLoader.ventureLoader(CardLoader.loadCards(0, "ventureCards"));
    }
    private ArrayList<Territory> getTerritories() {
        return CardLoader.territoryLoader(CardLoader.loadCards(0, "territoryCards"));
    }

    protected Player getPlayer() {
        return new Player("", PlayerColor.YELLOW);
    }

    protected Board getBoard() {
        List<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("a", PlayerColor.YELLOW);
        playerArrayList.add(player1);
        Player player2 = new Player("b", PlayerColor.RED);
        playerArrayList.add(player2);
        return new Board(playerArrayList);
    }

    protected Resources getResources() {
        return new Resources(new Woods(1), new Stones(1), new Servants(1), new Money(1),
                new MilitaryPoints(1), new FaithPoints(1), new CouncilPrivileges(0), new VictoryPoints(1));
    }

    protected Character getWarlord() {
        return getCharacters().get(0);
    }

    protected Building getTheater() {
        return getBuildings().get(0);
    }

    protected Venture getHiringRecruits() {
        return getVentures().get(0);
    }

    protected Territory getMonastery() {
        return getTerritories().get(0);
    }
}