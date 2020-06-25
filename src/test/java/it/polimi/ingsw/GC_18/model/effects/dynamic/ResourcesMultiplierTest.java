package it.polimi.ingsw.GC_18.model.effects.dynamic;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;
import it.polimi.ingsw.GC_18.model.resources.Money;

/**
 * Tests the ResourcesMultiplier effect.
 */
public class ResourcesMultiplierTest extends EffectTest{
    @Test
    public void testApply() {

        ArrayList<Source> sources = new ArrayList<>();
        sources.add(Source.LEADER_PERMANENT);

        ResourcesMultiplier resourcesMultiplier = new ResourcesMultiplier(getResources(), sources, 3);
        Player player = getPlayer();
        player.addObserver(resourcesMultiplier);
        player.addMoney(3, new NotifyResource(new Money(3), Source.LEADER_PERMANENT));
        assertTrue(player.getMoney() == 9);
    }

}