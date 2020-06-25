package it.polimi.ingsw.GC_18.model.effects.decorators;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.Effect;
import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;

/**
 * Decorator class for applying two immediate effects.
 */
public class DoubleEffectImmediate implements ImmediateEffect, Serializable {
    
    private static final long serialVersionUID = -6087442393062447924L;
    
    private Effect effect1;
    private Effect effect2;

    /**
     * Creates a new double effect immediate decorating two effects
     * @param effect1 the first effect to be decorated.
     * @param effect2 the second effect to be decorated.
     */
    public DoubleEffectImmediate(Effect effect1, Effect effect2) {
        this.effect1 = effect1;
        this.effect2 = effect2;

        if (effect1 instanceof SourceNotifier) {
            ((SourceNotifier) effect1).setSource(Source.IMMEDIATE_EFFECT);
        }

        if (effect2 instanceof SourceNotifier) {
            ((SourceNotifier) effect2).setSource(Source.IMMEDIATE_EFFECT);
        }
    }

    /**
     * Applies the two effects to the player.
     * @param player the player the effects are applied to
     */
    @Override
    public void apply(Player player) {
        effect1.apply(player);
        effect2.apply(player);
    }

    @Override
    public String toString() {
        return "Double immediate effect: \n" + "1-"+effect1.toString() + "2-"+effect2.toString();
    }

}
