package bci.rules;

import bci.Work;
import bci.exceptions.InvalidRuleException;
import bci.user.User;

public class R1 extends Rule {

    public R1() {
        super(1);
    }
    
    @Override
    public boolean isValid(User user, Work work) throws InvalidRuleException {
        if (!user.hasBorrowedWork(work)) {
            return true;
        }
        throw new InvalidRuleException(getRuleId());
    }
}
