package it.polimi.ingsw.GC_18.model.effects.dynamic;

import java.io.Serializable;
import java.util.Observable;
import java.util.logging.Level;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.actions.Action;
import it.polimi.ingsw.GC_18.model.effects.DynamicEffect;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyAction;

/**
 * This class represents a dynamic effect that adds value to an Action in a production place.
 */
public class ProductionChange implements DynamicEffect, Serializable {
    
    private static final long serialVersionUID = -2172083563508452176L;
    
    private int value;

    /**
     * Creates a new ProductionChange effect.
     * @param value the value of the change.
     */
    public ProductionChange(int value) {
        this.value = value;
    }

    /**
     * Using a NotifyAction applies the effect.
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            NotifyAction notifyAction = (NotifyAction) arg;
            Action action = notifyAction.getActionPlace();
            if (action.isProductionArea()) {
                action.changeActionValue(value);
            }
        } catch (ClassCastException e) {
            ModelLogger.getInstance().log(Level.INFO, "Notify thrown", e);
        }
    }

    @Override
    public String toString() {
        return "Adds " + value + " value to an action done in a production place";
    }
    
}
