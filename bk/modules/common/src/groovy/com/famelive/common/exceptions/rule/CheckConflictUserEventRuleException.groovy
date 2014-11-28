package com.famelive.common.exceptions.rule

class CheckConflictUserEventRuleException extends RuleException {

    CheckConflictUserEventRuleException() {}

    CheckConflictUserEventRuleException(String message) {
        super(message)
    }

    CheckConflictUserEventRuleException(String message, Throwable cause) {
        super(message, cause)
    }

    CheckConflictUserEventRuleException(Throwable cause) {
        super(cause)
    }

}
