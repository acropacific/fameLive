package com.famelive.api.exceptions

class ApiUserNotFoundException extends ApiValidationException {

    ApiUserNotFoundException() {
    }

    ApiUserNotFoundException(String message) {
        super(message)
    }

    ApiUserNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiUserNotFoundException(Throwable cause) {
        super(cause)
    }
}
