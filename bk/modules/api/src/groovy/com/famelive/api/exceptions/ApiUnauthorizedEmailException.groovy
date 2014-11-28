package com.famelive.api.exceptions

class ApiUnauthorizedEmailException extends ApiValidationException {

    ApiUnauthorizedEmailException() {
    }

    ApiUnauthorizedEmailException(String message) {
        super(message)
    }

    ApiUnauthorizedEmailException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiUnauthorizedEmailException(Throwable cause) {
        super(cause)
    }
}
