package bci.rules;

import bci.Work;
import bci.exceptions.InvalidRuleException;
import bci.user.User;

public class R2 extends Rule {

    public R2() {
        super(2);
    }
    
    @Override
    public boolean isValid(User user, Work work) throws InvalidRuleException {
        if (user.getStatus().canRequest()) {
            return true;
        }
        throw new InvalidRuleException(getRuleId());
    }
}