package bci.exceptions;

public class InvalidRuleException extends Exception {

    private final int _ruleId;

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public InvalidRuleException(int ruleId) {
        _ruleId = ruleId;
    }

    public int getRuleId() {
        return _ruleId;
    }
}
