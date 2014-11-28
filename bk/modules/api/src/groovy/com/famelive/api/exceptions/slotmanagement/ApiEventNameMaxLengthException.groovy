package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiEventNameMaxLengthException extends ApiValidationException {

    ApiEventNameMaxLengthException() {
    }

    ApiEventNameMaxLengthException(String message) {
        super(message)
    }

    ApiEventNameMaxLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiEventNameMaxLengthException(Throwable cause) {
        super(cause)
    }
}
