package bci;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import bci.notifications.Notification;
import bci.notifications.NotificationType;
import bci.notifications.Observer;


public abstract class Work implements Serializable, Comparable<Work>, Notification {

    private int _workID;
    private String _title;
    private int _price;
    private Category _category;
    private int _copies;
    private int _availableCopies;
    private Map<NotificationType, List<Observer>> _interestedUsers = new HashMap<>();

    public Work(int workId, String title, int price, Category category, int copies){
        _workID = workId;
        _title = title;
        _price = price;
        _copies = copies;
        _category = category;
        _availableCopies = copies;
        for (NotificationType type : NotificationType.values()) {
            _interestedUsers.put(type, new ArrayList<>());
        }
    }

    public int getWorkId() {
        return _workID;
    }
    
    public String getTitle() {
        return _title;
    }

    public int getPrice() {
        return _price;
    }

    public Category getCategory() {
        return _category;
    }

    public String getCategoryString() {
        return switch(_category) {
            case FICTION -> "Ficção";
            case SCITECH -> "Técnica e Científica";
            case REFERENCE -> "Referência";
        };
    }

    public int getCopies() {
        return _copies;
    }

    public int getAvailableCopies() {
        return _availableCopies;
    }

    public void changeCopies(int value) {
        _availableCopies += value;
        _copies += value;
    }

    public void changeAvailableCopies(int value) {
        _availableCopies += value;
    }
    
    @Override
    public int compareTo(Work work) {
        return this._title.compareToIgnoreCase(work._title);
    }

    public abstract String getCreatorName();

    public abstract List<Creator> getCreators();

    @Override
    public void registerObserver(NotificationType type, Observer observer) {
        _interestedUsers.get(type).add(observer);
    }

    @Override
    public void removeObserver(NotificationType type, Observer observer) {
        _interestedUsers.get(type).remove(observer);
    }

    @Override
    public void notifyObservers(NotificationType type, Work work) {
        _interestedUsers.get(type).forEach(obs -> obs.update(type, work));
    }
}