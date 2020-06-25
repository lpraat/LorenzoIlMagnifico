package it.polimi.ingsw.GC_18.model.notifiers;

import it.polimi.ingsw.GC_18.model.Notification;

/**
 * This class represent a notify for a church report.
 */
public class NotifyReport {
    private Notification notification;

    /**
     * Creates a new report notify.
     * @param notification the type of notification.
     */
    public NotifyReport(Notification notification) {
        this.notification = notification;
    }

    /**
     * @return the notification.
     */
    public Notification getNotification() {
        return notification;
    }
}
