package com.famelive.api.exceptions

class ApiBlankForgotPasswordCodeException extends ApiValidationException {

    ApiBlankForgotPasswordCodeException() {
    }

    ApiBlankForgotPasswordCodeException(String message) {
        super(message)
    }

    ApiBlankForgotPasswordCodeException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankForgotPasswordCodeException(Throwable cause) {
        super(cause)
    }
}
