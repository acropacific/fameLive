package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankEventDurationException extends ApiValidationException {

    ApiBlankEventDurationException() {
    }

    ApiBlankEventDurationException(String message) {
        super(message)
    }

    ApiBlankEventDurationException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEventDurationException(Throwable cause) {
        super(cause)
    }
}
