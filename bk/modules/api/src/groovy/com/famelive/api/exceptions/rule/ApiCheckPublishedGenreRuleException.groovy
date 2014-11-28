package com.famelive.api.exceptions.rule

class ApiCheckPublishedGenreRuleException extends ApiRuleException {

    ApiCheckPublishedGenreRuleException() {}

    ApiCheckPublishedGenreRuleException(String message) {
        super(message)
    }

    ApiCheckPublishedGenreRuleException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiCheckPublishedGenreRuleException(Throwable cause) {
        super(cause)
    }

}
