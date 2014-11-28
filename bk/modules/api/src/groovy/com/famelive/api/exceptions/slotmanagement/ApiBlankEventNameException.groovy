package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankEventNameException extends ApiValidationException {

    ApiBlankEventNameException() {
    }

    ApiBlankEventNameException(String message) {
        super(message)
    }

    ApiBlankEventNameException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEventNameException(Throwable cause) {
        super(cause)
    }
}
