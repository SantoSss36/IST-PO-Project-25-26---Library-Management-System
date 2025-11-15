package bci.user;

public class Normal extends Behaviour {

    public Normal(User user) {
        super(user);
    }

    @Override
    public void dutiful() {
        _user.setBehaviour(new Dutiful(_user));
    }

    @Override
    public void normal() {}

    @Override
    public void defaulter() {
        _user.setBehaviour(new Defaulter(_user));
    }

    @Override
    public boolean canRequestHighPrice() {
        return false;
    }
    
    @Override
    public int borOneCopyPeriod(){
        return 3;
    }

    @Override
    public int borFiveLessCopyPeriod(){
        return 8;
    }

    @Override
    public int borFiveMoreCopyPeriod(){
        return 15;
    }

    @Override
    public int howManyCanRequest() {
        return 3;
    }

    @Override
    public String toString() {
        return "NORMAL";
    }
}