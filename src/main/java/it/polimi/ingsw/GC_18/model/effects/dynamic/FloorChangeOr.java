package it.polimi.ingsw.GC_18.model.effects.dynamic;

import java.io.Serializable;
import java.util.Observable;
import java.util.logging.Level;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;
import it.polimi.ingsw.GC_18.model.actions.TowerAction;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyAction;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents a dynamic effect that adds value to a TowerAction and asks the player
 * which type of discount he wants to apply.
 */
public class FloorChangeOr extends FloorChange implements Blocking, Serializable {
    
    private static final long serialVersionUID = 2010756659065348121L;
    
    private Resources resources1;
    private Resources resources2;

    /**
     * Creates a new floor change effect.
     * @param towerColor the color of the tower place the effect is applied to
     * @param value the action value added
     * @param resources1 the first option discount for buying the card.
     * @param resources2 the second option discount for buying the card.
     */
    public FloorChangeOr(TowerColor towerColor, int value, Resources resources1, Resources resources2) {
        super(towerColor, value, null);
        this.resources1 = resources1;
        this.resources2 = resources2;
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    /**
     * Using a NotifyAction applies the effect.
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            Player player = (Player) o;
            NotifyAction notifyAction = (NotifyAction) arg;
            TowerAction towerAction = (TowerAction) notifyAction.getActionPlace();
            Notification notification = notifyAction.getNotification();

            if (towerAction.getFloor().getTower().getColor().equals(towerColor) || towerColor.equals(TowerColor.ANY)) {
                if (Notification.COLOR.equals(notification)) {
                    String response = block(player, WaitingCommand.FLOORCHANGE_OR);
                    if ("1".equals(response) && resources1 != null) {
                        towerAction.increaseResources(resources1);
                    } else if ("2".equals(response) && resources2 != null) {
                        towerAction.increaseResources(resources2);
                    }
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
        return "Adds " + value +" value to a TowerAction plus gives one of the following discount: \n" + "1-"+resources1.toString()
                + "\n" + "2-"+resources2.toString();
    }
    
}
