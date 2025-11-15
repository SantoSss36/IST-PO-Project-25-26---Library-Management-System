package bci.user;

public class Suspended extends Status{
    
    public Suspended(User user) {
        super(user);
    }

    @Override
    public void active() {
        _user.setStatus(new Active(_user));
    }

    @Override
    public void suspended() {}

    @Override
    public boolean canRequest() {
        return false;
    }

    @Override
    public boolean mustPayFine() {
        return true;
    }

    @Override
    public String toString() {
        return "SUSPENSO - EUR " + _user.getFine();
    }
}
