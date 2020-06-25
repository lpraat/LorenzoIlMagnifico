package it.polimi.ingsw.GC_18.model.cards;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;
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
 * Tests the requirement of a leader card.
 */
public class RequirementTest {
    private Player player;

    @Before
    public void createPlayer() {
        ArrayList<Building> buildings1 = CardLoader.buildingLoader(CardLoader.loadCards(0, "buildingCards"));
        player = new Player("test", PlayerColor.BLUE);
        player.getPersonalBoard().getBuildings().add(buildings1.get(0));
        player.getPersonalBoard().getBuildings().add(buildings1.get(1));
        player.getPersonalBoard().getBuildings().add(buildings1.get(2));
        player.setServants(3);
    }


    @Test
    public void checkPlayable() {
        assertTrue(new Requirement(0, 0, 3, 0, true, 3, null).checkPlayable(player));
        assertTrue(new Requirement(2, 0, 0, 0, false, 0,
                new Resources(new Woods(0), new Stones(0), new Servants(3), new Money(0), new MilitaryPoints(0),
                              new FaithPoints(0), new CouncilPrivileges(0), new VictoryPoints(0))).checkPlayable(player));
    }

}