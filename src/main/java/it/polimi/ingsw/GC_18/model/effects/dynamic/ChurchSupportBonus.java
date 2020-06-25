package it.polimi.ingsw.GC_18.model.effects.dynamic;

import java.io.Serializable;
import java.util.Observable;
import java.util.logging.Level;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.DynamicEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyReport;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents a dynamic effect that applies a bonus whenever a church support is done.
 */
public class ChurchSupportBonus extends SourceNotifier implements DynamicEffect, Serializable {
    
    private static final long serialVersionUID = -2487635563693447219L;
    
    private Resources resources;

    /**
     * Creates a new ChurchSupportBonus
     * @param resources the resources bonus to give to the player.
     */
    public ChurchSupportBonus(Resources resources) {
        this.resources = resources;
        source = Source.LEADER_PERMANENT;
    }

    /**
     * Using a NotifyReport applies the effect.
     */
    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) o;
        try {
            NotifyReport notifyReport = (NotifyReport) arg;
            if (notifyReport.getNotification().equals(Notification.CHURCH_SUPPORT)) {
                resources.addResources(player, source);
            }
        } catch (ClassCastException e) {
            ModelLogger.getInstance().log(Level.INFO, "Notify thrown", e);
        }
    }

    @Override
    public String toString() {
        return "Gives this bonus whenever a church support is done: \n" + resources.toString();
    }
    
}
