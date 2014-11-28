package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankEventStatusException extends ApiValidationException {

    ApiBlankEventStatusException() {
    }

    ApiBlankEventStatusException(String message) {
        super(message)
    }

    ApiBlankEventStatusException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEventStatusException(Throwable cause) {
        super(cause)
    }
}
