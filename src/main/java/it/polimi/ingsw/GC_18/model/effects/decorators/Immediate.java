package it.polimi.ingsw.GC_18.model.effects.decorators;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.Effect;
import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;

/**
 * Decorator class that takes an effect and makes it an Immediate one.
 */
public class Immediate implements ImmediateEffect, Serializable {
    
    private static final long serialVersionUID = 4392793273765508781L;
    
    private Effect effect;

    /**
     * Creates a new immediate effect decorating an effect.
     * @param effect the effect to be decorated.
     */
    public Immediate(Effect effect) {
        this.effect = effect;

        if (effect instanceof SourceNotifier) {
            ((SourceNotifier) effect).setSource(Source.IMMEDIATE_EFFECT);
        }
    }

    @Override
    public void apply(Player player) {
        effect.apply(player);
    }
    
}