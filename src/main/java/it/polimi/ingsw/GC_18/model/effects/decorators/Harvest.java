package it.polimi.ingsw.GC_18.model.effects.decorators;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.Effect;
import it.polimi.ingsw.GC_18.model.effects.HarvestEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;

/**
 * Decorator class that takes an effect and makes it a Harvest one.
 */
public class Harvest implements HarvestEffect, Serializable {
    private static final long serialVersionUID = 1065845003057331240L;
    
    private int activationValue;
    private Effect effect;

    /**
     * Creates a new Harvest effect.
     * @param activationValue the activation value for applying the effect.
     * @param effect the effect.
     */
    public Harvest(int activationValue, Effect effect) {
        this.effect = effect;
        this.activationValue = activationValue;
        if (effect instanceof SourceNotifier) {
            ((SourceNotifier) effect).setSource(Source.PERMANENT_EFFECT);
        }
    }


    /**
     * @param activationValue the value of the harvest action.
     * @return true if the harvest effect is activable.
     */
    @Override
    public boolean isActivable(int activationValue) {
        if (activationValue >= this.activationValue) {
            return true;
        }
        return false;
    }

    @Override
    public void apply(Player player) {
        effect.apply(player);
    }

    @Override
    public String toString() {
        return "Harvest Effect with activation value of " + activationValue + " :" + "\n" + effect.toString();
    }
    
}
