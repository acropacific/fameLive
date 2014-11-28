package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankEventIdException extends ApiValidationException {

    ApiBlankEventIdException() {
    }

    ApiBlankEventIdException(String message) {
        super(message)
    }

    ApiBlankEventIdException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEventIdException(Throwable cause) {
        super(cause)
    }
}
