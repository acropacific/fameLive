package com.famelive.api.exceptions

class ApiInvalidForgotPasswordCodeException extends ApiValidationException {

    ApiInvalidForgotPasswordCodeException() {
    }

    ApiInvalidForgotPasswordCodeException(String message) {
        super(message)
    }

    ApiInvalidForgotPasswordCodeException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidForgotPasswordCodeException(Throwable cause) {
        super(cause)
    }
}
