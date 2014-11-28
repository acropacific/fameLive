package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankEventImageNameException extends ApiValidationException {

    ApiBlankEventImageNameException() {
    }

    ApiBlankEventImageNameException(String message) {
        super(message)
    }

    ApiBlankEventImageNameException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEventImageNameException(Throwable cause) {
        super(cause)
    }
}
