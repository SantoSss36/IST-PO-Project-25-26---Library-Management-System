package bci.rules;

import bci.Work;
import bci.exceptions.InvalidRuleException;
import bci.user.User;

public class R3 extends Rule {

    public R3() {
        super(3);
    }
    
    @Override
    public boolean isValid(User user, Work work) throws InvalidRuleException {
        if(work.getAvailableCopies() > 0) {
            return true;
        }
        throw new InvalidRuleException(getRuleId());
    }
}