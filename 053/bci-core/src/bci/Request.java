package bci;

import java.io.Serializable;

import bci.user.User;

public class Request implements Serializable {

    private User _user;
    private Work _work;
    private int _limitDate;

    public Request(User user, Work work, int limitDate) {
        _user = user;
        _work = work;
        _limitDate = limitDate;
    }

    public User getUser() {
        return _user;
    }

    public Work getWork() {
        return _work;
    }

    public int getLimitDate() {
        return _limitDate;
    }
}
