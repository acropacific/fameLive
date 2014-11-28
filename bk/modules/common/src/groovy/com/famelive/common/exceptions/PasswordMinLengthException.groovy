package com.famelive.common.exceptions

class PasswordMinLengthException extends ValidationException {

    PasswordMinLengthException() {}

    PasswordMinLengthException(String message) {
        super(message)
    }

    PasswordMinLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    PasswordMinLengthException(Throwable cause) {
        super(cause)
    }
}
