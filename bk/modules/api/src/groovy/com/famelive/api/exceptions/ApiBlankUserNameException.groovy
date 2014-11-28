package com.famelive.api.exceptions

class ApiBlankUserNameException extends ApiValidationException {

    ApiBlankUserNameException() {
    }

    ApiBlankUserNameException(String message) {
        super(message)
    }

    ApiBlankUserNameException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankUserNameException(Throwable cause) {
        super(cause)
    }
}
