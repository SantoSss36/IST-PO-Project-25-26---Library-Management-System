package bci.rules;

import bci.Work;
import bci.exceptions.InvalidRuleException;
import bci.user.User;

public class R4 extends Rule {

    public R4() {
        super(4);
    }

    @Override
    public boolean isValid(User user, Work work) throws InvalidRuleException {
        if (user.getNumberOfBorrowedWorks() < user.getBehaviour().howManyCanRequest()) {
            return true;
        }
        throw new InvalidRuleException(getRuleId());
    }
}
