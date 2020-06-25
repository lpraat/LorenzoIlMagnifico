package it.polimi.ingsw.GC_18.server.controller;

import it.polimi.ingsw.GC_18.model.TowerColor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lpraat on 08/07/2017.
 */
public class WaitingCommandTest {
    @Test
    public void getFloorCommand() throws Exception {
        assertEquals(WaitingCommand.EXTRA_BUILDING, WaitingCommand.getFloorCommand(TowerColor.YELLOW));
        assertEquals(WaitingCommand.EXTRA_CHARACTER, WaitingCommand.getFloorCommand(TowerColor.BLUE));
        assertEquals(WaitingCommand.EXTRA_VENTURE, WaitingCommand.getFloorCommand(TowerColor.PURPLE));
        assertEquals(WaitingCommand.EXTRA_TERRITORY, WaitingCommand.getFloorCommand(TowerColor.GREEN));
    }
}