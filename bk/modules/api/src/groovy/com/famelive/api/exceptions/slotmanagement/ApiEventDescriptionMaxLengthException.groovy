package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiEventDescriptionMaxLengthException extends ApiValidationException {

    ApiEventDescriptionMaxLengthException() {
    }

    ApiEventDescriptionMaxLengthException(String message) {
        super(message)
    }

    ApiEventDescriptionMaxLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiEventDescriptionMaxLengthException(Throwable cause) {
        super(cause)
    }
}
