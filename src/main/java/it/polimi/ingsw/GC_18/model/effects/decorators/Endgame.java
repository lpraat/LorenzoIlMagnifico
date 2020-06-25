package it.polimi.ingsw.GC_18.model.effects.decorators;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.Effect;
import it.polimi.ingsw.GC_18.model.effects.EndgameEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;

/**
 * Decorator class that takes an effect and makes it an Endgame one.
 */
public class Endgame implements EndgameEffect, Serializable {
    
    private static final long serialVersionUID = -8615207743900446863L;
    
    private Effect effect;

    /**
     * Creates a new EndGame effect decorating an effect.
     * @param effect the effect to be decorated.
     */
    public Endgame(Effect effect) {
        this.effect = effect;

        if (effect instanceof SourceNotifier) {
            ((SourceNotifier) effect).setSource(Source.PERMANENT_EFFECT);
        }
    }

    @Override
    public String toString() {
        return "Endgame Effect: \n" + effect.toString();
    }

    @Override
    public void apply(Player player) {
        effect.apply(player);
    }
    
}
