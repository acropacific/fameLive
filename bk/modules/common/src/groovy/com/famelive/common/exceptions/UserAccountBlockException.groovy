package com.famelive.common.exceptions

class UserAccountBlockException extends ValidationException {

    UserAccountBlockException() {}

    UserAccountBlockException(String message) {
        super(message)
    }

    UserAccountBlockException(String message, Throwable cause) {
        super(message, cause)
    }

    UserAccountBlockException(Throwable cause) {
        super(cause)
    }
}
