package bci.user;

public class Dutiful extends Behaviour {

    public Dutiful(User user) {
        super(user);
    }

    @Override
    public void dutiful() {}

    @Override
    public void normal() {
        _user.setBehaviour(new Normal(_user));
    }

    @Override
    public void defaulter() {
        _user.setBehaviour(new Defaulter(_user));
    }

    @Override
    public boolean canRequestHighPrice() {
        return true;
    }

    @Override
    public int borOneCopyPeriod(){
        return 8;
    }

    @Override
    public int borFiveLessCopyPeriod(){
        return 15;
    }

    @Override
    public int borFiveMoreCopyPeriod(){
        return 30;
    }

    @Override
    public int howManyCanRequest() {
        return 5;
    }

    @Override
    public String toString() {
        return "CUMPRIDOR";
    }
}
