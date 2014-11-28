package com.famelive.api.exceptions

class ApiBlankEmailException extends ApiValidationException {

    ApiBlankEmailException() {
    }

    ApiBlankEmailException(String message) {
        super(message)
    }

    ApiBlankEmailException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEmailException(Throwable cause) {
        super(cause)
    }
}
