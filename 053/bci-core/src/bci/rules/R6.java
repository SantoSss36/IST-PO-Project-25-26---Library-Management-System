package bci.rules;

import bci.Work;
import bci.exceptions.InvalidRuleException;
import bci.user.User;

public class R6 extends Rule {

    public R6() {
        super(6);
    }
    
    @Override
    public boolean isValid(User user, Work work) throws InvalidRuleException {
        if (work.getPrice() > 25) {
            if (user.getBehaviour().canRequestHighPrice()) {
                return true;
            }
            else {
                throw new InvalidRuleException(getRuleId());
            }
        }
        return true;
    }
}
