package it.polimi.ingsw.GC_18.model.effects.dynamic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyReport;

/**
 * Tests the ChurchSupportBonus effect.
 */
public class ChurchSupportBonusTest extends EffectTest {

    @Test
    public void testApply() {
        Player player = getPlayer();
        ChurchSupportBonus churchSupportBonus = new ChurchSupportBonus(getResources());
        player.addObserver(churchSupportBonus);
        NotifyReport notifyReport = new NotifyReport(Notification.CHURCH_SUPPORT);
        player.notifyVaticanReport(notifyReport);
        assertEquals(1, player.getWoods());
        assertEquals(1,  player.getStones());
        assertEquals(1, player.getServants());
        assertEquals(1, player.getMoney());
        assertEquals(1, player.getMilitaryPoints());
        assertEquals(1, player.getFaithPoints());
        assertEquals(1, player.getVictoryPoints());


    }
}