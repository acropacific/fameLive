package com.famelive.api.exceptions

class ApiInvalidTokenException extends ApiException {

    ApiInvalidTokenException() {
    }

    ApiInvalidTokenException(String message) {
        super(message)
    }

    ApiInvalidTokenException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidTokenException(Throwable cause) {
        super(cause)
    }
}
