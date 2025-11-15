package bci.user;

import bci.Work;
import bci.notifications.NotificationType;
import bci.notifications.Observer;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class User implements Serializable, Comparable<User>, Observer {

    private int _userID;
    private String _name;
    private String _email;
    private Status _status = new Active(this);
    private Behaviour _behaviour = new Normal(this);
    private int _fine;
    private List<Work> _borrowedWorks = new ArrayList<>();
    private int _streakRequests = 0;
    private List<String> _notifications = new ArrayList<>();

    public User(int userId, String name, String email){
        _userID = userId;
        _name = name;
        _email = email;
    }

    public int getId() {
        return _userID;
    }

    public String getName() {
        return _name;
    }

    public String getEmail() {
        return _email;
    }

    public void active() {
        _status.active();
    }

    public void suspended() {
        _status.suspended();
    }

    public void setStatus(Status status) {
        _status = status;
    }

    public Status getStatus() {
        return _status;
    }

    public void dutiful() {
        _behaviour.dutiful();
    }

    public void normal() {
        _behaviour.normal();
    }

    public void defaulter() {
        _behaviour.defaulter();
    }

    public void setBehaviour(Behaviour behaviour) {
        _behaviour = behaviour;
    }

    public Behaviour getBehaviour() {
        return _behaviour;
    }

    public int getFine() {
        return _fine;
    }

    public void addBorrowedWork(Work work) {
        _borrowedWorks.add(work);
    }

    public void removeBorrowedWork(Work work) {
        _borrowedWorks.remove(work);
    }

    public boolean hasBorrowedWork(Work work) {
        return _borrowedWorks.contains(work);
    }

    public int getNumberOfBorrowedWorks() {
        return _borrowedWorks.size();
    }

    public void addFine(int amount) {
        _fine += amount;
    }

    public void payFine(int amount) {
        _fine -= amount;
    }

    public int getStreakRequests() {
        return _streakRequests;
    }

    public void updateStreakRequests(int value) {
        if (value < 0 && _streakRequests >= 5) {  // cumpridor --> normal
            _streakRequests = 0;
            _behaviour.normal();
        }
        if (value < 0 && _streakRequests > 0) {  // quebra a streak dentro do prazo
            _streakRequests = 0;
        }
        if (value > 0 && _streakRequests < 0) {  // quebra a streak de atrasos
            _streakRequests = 0;
        }
        _streakRequests += value;

        if(_streakRequests == -3) {              // normal --> faltoso    
            _behaviour.defaulter();
        } 
        else if (_streakRequests == 3) {         // faltoso --> normal
            _behaviour.normal();
        }
        else if (_streakRequests == 5) {         // normal --> cumpridor
            _behaviour.dutiful();
        }
    }

    public List<String> getNotifications() {
        return _notifications;
    }

    public void cleanNotifications() {
        _notifications.clear();
    }

    public void removeNotification (NotificationType type, Work work) {
        String notifToRemove = "" + type + ": " + work;

        for (String notif : _notifications) {
            if (notif.equals(notifToRemove)) {
                _notifications.remove(notif);
            }
        }
    }
    
    @Override
    public int compareTo(User user) {
        return this._name.compareToIgnoreCase(user._name);
    }

    @Override
    public void update(NotificationType type, Work work) {
        String notif = "" + type + ": " + work;
        _notifications.add(notif);
    }

    @Override
    public String toString() {
        return "" + getId() + " - " + getName() + " - " + getEmail() +  " - " + getBehaviour() + " - " + getStatus(); 
    }
}
