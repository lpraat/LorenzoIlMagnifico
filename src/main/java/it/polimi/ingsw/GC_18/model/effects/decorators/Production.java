package it.polimi.ingsw.GC_18.model.effects.decorators;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.Effect;
import it.polimi.ingsw.GC_18.model.effects.ProductionEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;

/**
 * Decorator class that takes an effect and make it a Production one.
 */
public class Production implements ProductionEffect, Serializable {

    private static final long serialVersionUID = -8417265142988606542L;
    
    private int activationValue;
    private Effect effect;

    /**
     * Creates a new production effect decorating an effect.
     * @param activationValue the activation value for applying the effect.
     * @param effect the effect to be decorated.
     */
    public Production(int activationValue, Effect effect) {
        this.effect = effect;
        this.activationValue = activationValue;

        if (effect instanceof SourceNotifier) {
            ((SourceNotifier) effect).setSource(Source.PERMANENT_EFFECT);
        }
    }

    @Override
    public void apply(Player player) {
        effect.apply(player);
    }

    /**
     * @param activationValue the value of the production action.
     * @return true if the production effect is activable.
     */
    @Override
    public boolean isActivable(int activationValue) {
        if (activationValue >= this.activationValue) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Production Effect with activation value of " + activationValue + " :" + "\n" + effect.toString();
    }
    
}
