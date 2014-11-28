package com.famelive.api.exceptions

class ApiInvalidVerificationTokenException extends ApiValidationException {

    ApiInvalidVerificationTokenException() {
    }

    ApiInvalidVerificationTokenException(String message) {
        super(message)
    }

    ApiInvalidVerificationTokenException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidVerificationTokenException(Throwable cause) {
        super(cause)
    }
}
