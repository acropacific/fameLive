package com.famelive.common.exceptions

class InvalidForgotPasswordCodeException extends ValidationException {

    InvalidForgotPasswordCodeException() {}

    InvalidForgotPasswordCodeException(String message) {
        super(message)
    }

    InvalidForgotPasswordCodeException(String message, Throwable cause) {
        super(message, cause)
    }

    InvalidForgotPasswordCodeException(Throwable cause) {
        super(cause)
    }
}
