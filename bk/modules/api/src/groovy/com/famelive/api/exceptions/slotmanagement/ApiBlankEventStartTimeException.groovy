package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankEventStartTimeException extends ApiValidationException {

    ApiBlankEventStartTimeException() {
    }

    ApiBlankEventStartTimeException(String message) {
        super(message)
    }

    ApiBlankEventStartTimeException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEventStartTimeException(Throwable cause) {
        super(cause)
    }
}
