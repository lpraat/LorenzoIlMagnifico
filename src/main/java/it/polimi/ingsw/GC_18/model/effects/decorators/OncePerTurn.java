package it.polimi.ingsw.GC_18.model.effects.decorators;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.Effect;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;

/**
 * Decorator class that takes an effect and make it an OncePerTurn one.
 */
public class OncePerTurn implements OncePerTurnEffect, Serializable {
    
    private static final long serialVersionUID = -4457384990377394065L;
    
    private Effect effect;
    private boolean activated;

    /**
     * Creates a new once per turn effect decorating an effect.
     * @param effect the effect to be decorated.
     */
    public OncePerTurn(Effect effect) {
        this.effect = effect;
        if (effect instanceof SourceNotifier) {
            ((SourceNotifier) effect).setSource(Source.ONCEPERTURN);
        }
    }

    /**
     * @return true if the effect has not been activated yet this turn.
     */
    @Override
    public boolean isActivable() {
        return !activated;
    }

    @Override
    public void apply(Player player) {
        effect.apply(player);
    }

    @Override
    public String toString() {
        return "OncePerTurnEffect :\n" + effect.toString();
    }

    /**
     * Sets the effect to activated.
     */
    @Override
    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    
}
