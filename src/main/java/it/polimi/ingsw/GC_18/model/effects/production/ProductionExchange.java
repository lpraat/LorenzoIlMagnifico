package it.polimi.ingsw.GC_18.model.effects.production;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.ProductionEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;

/**
 * This class represents an abstract production effect that let the player exchange resources.
 */
public abstract class ProductionExchange extends SourceNotifier implements ProductionEffect, Serializable {
    
    private static final long serialVersionUID = 204972125873246006L;
    
    private int activationValue;

    /**
     * Creates a new ProductionExchange effect.
     * @param activationValue the activation value.
     */
    ProductionExchange(int activationValue) {
        this.activationValue = activationValue;
        source = Source.PERMANENT_EFFECT;
    }

    /**
     * @param activationValue the activation value.
     * @return true if the effect is activable.
     */
    @Override
    public boolean isActivable(int activationValue) {
        if (activationValue >= this.activationValue) {
            return true;
        }
        return false;
    }

}
