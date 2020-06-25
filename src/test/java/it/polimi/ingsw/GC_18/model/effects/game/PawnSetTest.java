package it.polimi.ingsw.GC_18.model.effects.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.decorators.EffectTest;

/**
 * Tests the PawnSet effect.
 */
public class PawnSetTest extends EffectTest{

    @Test
    public void testApply() {
        Player player = getPlayer();
        List<DiceColor> diceColors = new ArrayList<>();
        diceColors.add(DiceColor.BLACK);
        diceColors.add(DiceColor.ORANGE);
        diceColors.add(DiceColor.WHITE);
        PawnSet pawnSet = new PawnSet(3, diceColors);
        pawnSet.apply(player);
        assertEquals(player.getBlackPawn().getValue(), 3);
        assertEquals(player.getOrangePawn().getValue(), 3);
        assertEquals(player.getWhitePawn().getValue(), 3);

    }

}