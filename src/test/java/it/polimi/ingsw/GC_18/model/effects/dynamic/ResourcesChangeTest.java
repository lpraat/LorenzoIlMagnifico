package it.polimi.ingsw.GC_18.model.effects.dynamic;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;

/**
 * Tests the ResourcesChange effect.
 */
public class ResourcesChangeTest extends EffectTest{
    @Test
    public void testApply() {

        ArrayList<Source> sources = new ArrayList<>();
        sources.add(Source.IMMEDIATE_EFFECT);
        Bonus bonus = new Bonus(getResources());

        ResourcesChange resourcesChange = new ResourcesChange(getResources(), sources);
        Player player = getPlayer();
        player.addObserver(resourcesChange);
        bonus.apply(player);
        assertTrue(player.getMoney()==2);

    }
}