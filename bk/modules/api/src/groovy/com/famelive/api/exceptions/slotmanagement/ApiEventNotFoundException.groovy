package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiEventNotFoundException extends ApiValidationException {

    ApiEventNotFoundException() {
    }

    ApiEventNotFoundException(String message) {
        super(message)
    }

    ApiEventNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiEventNotFoundException(Throwable cause) {
        super(cause)
    }
}
