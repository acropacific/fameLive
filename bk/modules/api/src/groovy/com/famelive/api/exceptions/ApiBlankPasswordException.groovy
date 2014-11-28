package com.famelive.api.exceptions

class ApiBlankPasswordException extends ApiValidationException {

    ApiBlankPasswordException() {
    }

    ApiBlankPasswordException(String message) {
        super(message)
    }

    ApiBlankPasswordException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankPasswordException(Throwable cause) {
        super(cause)
    }
}
