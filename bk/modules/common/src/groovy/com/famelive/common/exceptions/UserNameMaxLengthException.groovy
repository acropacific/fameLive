package com.famelive.common.exceptions

class UserNameMaxLengthException extends ValidationException {

    UserNameMaxLengthException() {}

    UserNameMaxLengthException(String message) {
        super(message)
    }

    UserNameMaxLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    UserNameMaxLengthException(Throwable cause) {
        super(cause)
    }
}
