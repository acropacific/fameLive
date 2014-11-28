package com.famelive.api.exceptions

class ApiFameNameMaxLengthException extends ApiValidationException {

    ApiFameNameMaxLengthException() {
    }

    ApiFameNameMaxLengthException(String message) {
        super(message)
    }

    ApiFameNameMaxLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiFameNameMaxLengthException(Throwable cause) {
        super(cause)
    }
}
