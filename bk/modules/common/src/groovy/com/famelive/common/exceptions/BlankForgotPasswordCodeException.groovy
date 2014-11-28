package com.famelive.common.exceptions

class BlankForgotPasswordCodeException extends ValidationException {

    BlankForgotPasswordCodeException() {}

    BlankForgotPasswordCodeException(String message) {
        super(message)
    }

    BlankForgotPasswordCodeException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankForgotPasswordCodeException(Throwable cause) {
        super(cause)
    }
}
