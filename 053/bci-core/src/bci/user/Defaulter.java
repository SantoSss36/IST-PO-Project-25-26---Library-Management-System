package bci.user;

public class Defaulter extends Behaviour {

    public Defaulter(User user) {
        super(user);
    }

    @Override
    public void dutiful() {}
    
    @Override
    public void normal() {
        _user.setBehaviour(new Normal(_user));
    }

    @Override
    public void defaulter() {}

    @Override
    public boolean canRequestHighPrice() {
        return false;
    }

    @Override
    public int borOneCopyPeriod(){
        return 2;
    }

    @Override
    public int borFiveLessCopyPeriod(){
        return 2;
    }

    @Override
    public int borFiveMoreCopyPeriod(){
        return 2;
    }

    @Override
    public int howManyCanRequest() {
        return 1;
    }

    @Override
    public String toString() {
        return "FALTOSO";
    }
}
