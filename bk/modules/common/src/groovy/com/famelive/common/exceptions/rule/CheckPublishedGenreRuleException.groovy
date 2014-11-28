package com.famelive.common.exceptions.rule

class CheckPublishedGenreRuleException extends RuleException {

    CheckPublishedGenreRuleException() {}

    CheckPublishedGenreRuleException(String message) {
        super(message)
    }

    CheckPublishedGenreRuleException(String message, Throwable cause) {
        super(message, cause)
    }

    CheckPublishedGenreRuleException(Throwable cause) {
        super(cause)
    }

}
