package bci.notifications;

import bci.Work;

public interface Observer {
    public void update(NotificationType type, Work work);
}
