package it.polimi.ingsw.GC_18.model.notifiers;

import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.actions.Action;

/**
 * This class represents a notify for an action or a tower action.
 */
public class NotifyAction {
    private Action action;
    private Notification notification;

    /**
     * Creates a new action notify.
     * @param action the action notified.
     * @param notification the type of notification.
     */
    public NotifyAction(Action action, Notification notification) {
        this.action = action;
        this.notification = notification;
    }

    /**
     * @return the action place of the notify.
     */
    public Action getActionPlace() {
        return action;
    }

    /**
     * @return the notification type.
     */
    public Notification getNotification() {
        return notification;
    }
}
