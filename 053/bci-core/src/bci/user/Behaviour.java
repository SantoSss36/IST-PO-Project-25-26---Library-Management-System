package bci.user;

import java.io.Serializable;

public abstract class Behaviour implements Serializable {
    protected User _user;

    public Behaviour(User user) {
        _user = user;
    }

    public abstract void dutiful();
    public abstract void normal();
    public abstract void defaulter();

    public abstract boolean canRequestHighPrice();

    public abstract int borOneCopyPeriod();
    public abstract int borFiveLessCopyPeriod();
    public abstract int borFiveMoreCopyPeriod();

    public abstract int howManyCanRequest();
}
