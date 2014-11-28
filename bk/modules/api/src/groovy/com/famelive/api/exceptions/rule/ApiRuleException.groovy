package com.famelive.api.exceptions.rule

import com.famelive.api.exceptions.ApiException

class ApiRuleException extends ApiException {

    ApiRuleException() {}

    ApiRuleException(String message) {
        super(message)
    }

    ApiRuleException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiRuleException(Throwable cause) {
        super(cause)
    }

}
