package com.famelive.api.exceptions.rule

class ApiCheckConflictUserEventRuleException extends ApiRuleException {

    ApiCheckConflictUserEventRuleException() {}

    ApiCheckConflictUserEventRuleException(String message) {
        super(message)
    }

    ApiCheckConflictUserEventRuleException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiCheckConflictUserEventRuleException(Throwable cause) {
        super(cause)
    }

}
