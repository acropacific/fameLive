package com.famelive.common.exceptions

class UserNotFoundException extends ValidationException {

    UserNotFoundException() {}

    UserNotFoundException(String message) {
        super(message)
    }

    UserNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    UserNotFoundException(Throwable cause) {
        super(cause)
    }
}
