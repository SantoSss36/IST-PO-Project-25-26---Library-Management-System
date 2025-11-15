package bci.rules;

import java.io.Serializable;
import bci.Work;
import bci.user.User;
import bci.exceptions.InvalidRuleException;

public abstract class Rule implements Serializable {
    private int _ruleId;

    public Rule(){}
    
    public Rule(int ruleId) {
        _ruleId = ruleId;
    }

    public int getRuleId() {
        return _ruleId;
    }

    public abstract boolean isValid(User user, Work work) throws InvalidRuleException;
}