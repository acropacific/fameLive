package com.famelive.api.exceptions

class ApiBlankVerificationTokenException extends ApiValidationException {

    ApiBlankVerificationTokenException() {
    }

    ApiBlankVerificationTokenException(String message) {
        super(message)
    }

    ApiBlankVerificationTokenException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankVerificationTokenException(Throwable cause) {
        super(cause)
    }
}
