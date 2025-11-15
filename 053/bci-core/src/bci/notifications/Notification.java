package bci.notifications;

import bci.Work;

public interface Notification {
    public void registerObserver(NotificationType type, Observer observer);
    public void removeObserver(NotificationType type, Observer observer);
    public void notifyObservers(NotificationType type, Work work);
}