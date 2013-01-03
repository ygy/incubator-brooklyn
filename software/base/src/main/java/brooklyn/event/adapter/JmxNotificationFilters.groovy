package brooklyn.event.adapter;

import javax.management.Notification
import javax.management.NotificationFilter

/**
 * @deprecated Use brooklyn.event.feed.jmx.JmxNotificationFilters instead
 */
@Deprecated
public class JmxNotificationFilters {

    public static NotificationFilter matchesType(String type) {
        return { Notification notif -> return type.equals(notif.getType()) } as NotificationFilter
    }
    
    public static NotificationFilter matchesTypeRegex(String typeRegex) {
        return { Notification notif -> return notif.getType().matches(typeRegex) } as NotificationFilter
    }
}
