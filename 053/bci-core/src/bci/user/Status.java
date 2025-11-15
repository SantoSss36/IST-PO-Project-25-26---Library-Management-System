package bci.user;

import java.io.Serializable;

public abstract class Status implements Serializable {
    protected User _user;

    public Status(User user) {
        _user = user;
    }

    public abstract void active();
    public abstract void suspended();

    public abstract boolean canRequest();
    public abstract boolean mustPayFine();
}
