package it.polimi.ingsw.GC_18.model.effects.immediate;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;

/**
 * Tests the BonusPerCard effect.
 */
public class BonusPerCardTest extends EffectTest {
    @Test
    public void testApply() {
        Player player = getPlayer();
        player.getPersonalBoard().getCharacters().add(getWarlord());
        player.getPersonalBoard().getTerritories().add(getMonastery());
        player.getPersonalBoard().getBuildings().add(getTheater());
        player.getPersonalBoard().getVentures().add(getHiringRecruits());
        BonusPerCard bonusPerCard1 = new BonusPerCard(getResources(), TowerColor.GREEN);
        BonusPerCard bonusPerCard2 = new BonusPerCard(getResources(), TowerColor.BLUE);
        BonusPerCard bonusPerCard3 = new BonusPerCard(getResources(), TowerColor.YELLOW);
        BonusPerCard bonusPerCard4 = new BonusPerCard(getResources(), TowerColor.BLUE);


        bonusPerCard1.apply(player);
        bonusPerCard2.apply(player);
        bonusPerCard3.apply(player);
        bonusPerCard4.apply(player);

        assertTrue(player.getWoods()==4);




    }


}