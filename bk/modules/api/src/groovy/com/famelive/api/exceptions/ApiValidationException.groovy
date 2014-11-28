package com.famelive.api.exceptions

class ApiValidationException extends ApiException {

    ApiValidationException() {
    }

    ApiValidationException(String message) {
        super(message)
    }

    ApiValidationException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiValidationException(Throwable cause) {
        super(cause)
    }
}
