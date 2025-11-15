package bci.rules;

import bci.Work;
import bci.exceptions.InvalidRuleException;
import bci.user.User;

public class R5 extends Rule {

    public R5() {
        super(5);
    }

    @Override
    public boolean isValid(User user, Work work) throws InvalidRuleException{
        if(work.getCategory().canRequestCategory()) {
            return true;
        }
        throw new InvalidRuleException(getRuleId());
    }
}
