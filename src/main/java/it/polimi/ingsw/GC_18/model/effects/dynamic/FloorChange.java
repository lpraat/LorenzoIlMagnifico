package it.polimi.ingsw.GC_18.model.effects.dynamic;

import java.io.Serializable;
import java.util.Observable;
import java.util.logging.Level;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.actions.TowerAction;
import it.polimi.ingsw.GC_18.model.effects.DynamicEffect;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyAction;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents a dynamic effect that adds value to a TowerAction and gives the player
 * a discount for buying the card.
 */
public class FloorChange implements DynamicEffect, Serializable {
    
    private static final long serialVersionUID = 5375169056984528629L;
    
    TowerColor towerColor;
    int value;
    private Resources resources;

    /**
     * Creates a new floor change effect.
     * @param towerColor the color of the tower place the effect is applied to
     * @param value the action value added
     * @param resources the discount for buying the card
     */
    public FloorChange(TowerColor towerColor, int value, Resources resources) {
        this.towerColor = towerColor;
        this.value = value;
        this.resources = resources;
    }

    /**
     * Using a NotifyAction applies the effect.
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            NotifyAction notifyAction = (NotifyAction) arg;
            TowerAction towerAction = (TowerAction) notifyAction.getActionPlace();
            Notification notification = notifyAction.getNotification();

            if (towerAction.getFloor().getTower().getColor().equals(towerColor) || towerColor.equals(TowerColor.ANY)) {
                if (Notification.COLOR.equals(notification) && resources != null) {
                    towerAction.increaseResources(resources);
                }
                if (Notification.VALUE.equals(notification) && value != 0) {
                    towerAction.changeActionValue(value);
                }
            }
        } catch (ClassCastException e) {
            ModelLogger.getInstance().log(Level.INFO, "Notify thrown", e);
        }
    }

    @Override
    public String toString() {
        return "Adds " + value +" value to a TowerAction plus gives the following discount: \n" + resources.toString();
    }
    
}
