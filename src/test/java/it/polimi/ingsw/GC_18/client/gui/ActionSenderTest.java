package it.polimi.ingsw.GC_18.client.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the command sent after gui click.
 */
public class ActionSenderTest {


    @Test
    public void testClick() {
        assertEquals("COUNCIL_SPOT", ActionSender.parseActionClick("councilSpotPawn"));
        assertEquals("COUNCIL_SPOT", ActionSender.parseActionClick("COUNCIL_SPOT"));

        assertEquals("MILITARY_SPOT", ActionSender.parseActionClick("militaryPawn"));
        assertEquals("MILITARY_SPOT", ActionSender.parseActionClick("MILITARY_SPOT"));

        assertEquals("COIN_SPOT", ActionSender.parseActionClick("coinPawn"));
        assertEquals("COIN_SPOT", ActionSender.parseActionClick("COIN_SPOT"));

        assertEquals("SERVANTS_SPOT", ActionSender.parseActionClick("servantsPawn"));
        assertEquals("SERVANTS_SPOT", ActionSender.parseActionClick("SERVANTS_SPOT"));

        assertEquals("COUNCIL_PALACE", ActionSender.parseActionClick("councilPawn1"));
        assertEquals("COUNCIL_PALACE", ActionSender.parseActionClick("councilPawn16"));
        assertEquals("FIGHT_SPOT", ActionSender.parseActionClick("FIGHT_SPOT"));

        assertEquals("HARVEST_AREA", ActionSender.parseActionClick("harvestPawn"));
        assertEquals("HARVEST_AREA", ActionSender.parseActionClick("HARVEST_AREA"));

        assertEquals("LARGEHARVEST_AREA", ActionSender.parseActionClick("largeHarvestPawn16"));
        assertEquals("LARGEHARVEST_AREA", ActionSender.parseActionClick("LARGEHARVEST_AREA"));

        assertEquals("PRODUCTION_AREA", ActionSender.parseActionClick("productionPawn"));
        assertEquals("PRODUCTION_AREA", ActionSender.parseActionClick("PRODUCTION_AREA"));

        assertEquals("LARGEPRODUCTION_AREA", ActionSender.parseActionClick("largeProductionPawn16"));
        assertEquals("LARGEPRODUCTION_AREA", ActionSender.parseActionClick("LARGEPRODUCTION_AREA"));


        assertEquals("TERRITORY4", ActionSender.parseActionClick("pawn1"));
        assertEquals("TERRITORY3", ActionSender.parseActionClick("pawn2"));
        assertEquals("TERRITORY2", ActionSender.parseActionClick("pawn3"));
        assertEquals("TERRITORY1", ActionSender.parseActionClick("pawn4"));
        assertEquals("CHARACTER4", ActionSender.parseActionClick("pawn5"));
        assertEquals("CHARACTER3", ActionSender.parseActionClick("pawn6"));
        assertEquals("CHARACTER2", ActionSender.parseActionClick("pawn7"));
        assertEquals("CHARACTER1", ActionSender.parseActionClick("pawn8"));
        assertEquals("BUILDING4", ActionSender.parseActionClick("pawn9"));
        assertEquals("BUILDING3", ActionSender.parseActionClick("pawn10"));
        assertEquals("BUILDING2", ActionSender.parseActionClick("pawn11"));
        assertEquals("BUILDING1", ActionSender.parseActionClick("pawn12"));
        assertEquals("VENTURE4", ActionSender.parseActionClick("pawn13"));
        assertEquals("VENTURE3", ActionSender.parseActionClick("pawn14"));
        assertEquals("VENTURE2", ActionSender.parseActionClick("pawn15"));
        assertEquals("VENTURE1", ActionSender.parseActionClick("pawn16"));



    }

}