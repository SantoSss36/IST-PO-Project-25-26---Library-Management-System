package bci.rules;

import java.util.List;
import java.util.ArrayList;
import bci.Work;
import bci.user.User;
import bci.exceptions.InvalidRuleException;

public class CompositeRule extends Rule {
    private List<Rule> _defaultRules = new ArrayList<>();

    public CompositeRule() {
        _defaultRules.add(new R1());
        _defaultRules.add(new R2());
        _defaultRules.add(new R3());
        _defaultRules.add(new R4());
        _defaultRules.add(new R5());
        _defaultRules.add(new R6());
    }

    public void addRule(Rule rule) {
        _defaultRules.add(rule);
    }

    public List<Rule> getRules() {
        return _defaultRules;
    }

    public Rule getRuleId(int ruleId) {
        return _defaultRules.get(ruleId);
    }

    @Override
    public boolean isValid(User user, Work work) throws InvalidRuleException {
        for (Rule rule : _defaultRules) {
            rule.isValid(user, work);
        }
        return true;
    }    
}
