package com.famelive.common.exceptions.rule

import com.famelive.common.exceptions.CommonException

class RuleException extends CommonException {

    RuleException() {}

    RuleException(String message) {
        super(message)
    }

    RuleException(String message, Throwable cause) {
        super(message, cause)
    }

    RuleException(Throwable cause) {
        super(cause)
    }

}
