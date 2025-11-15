package bci.user;

public class Active extends Status {
    
    public Active(User user) {
        super(user);
    }

    @Override
    public void suspended() {
        _user.setStatus(new Suspended(_user));
    }

    @Override
    public void active() {}

    @Override
    public boolean canRequest() {
        return true;
    }

    @Override
    public boolean mustPayFine() {
        return false;
    }

    @Override
    public String toString() {
        return "ACTIVO";
    }
}
