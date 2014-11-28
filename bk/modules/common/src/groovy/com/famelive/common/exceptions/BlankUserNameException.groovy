package com.famelive.common.exceptions

class BlankUserNameException extends ValidationException {

    BlankUserNameException() {}

    BlankUserNameException(String message) {
        super(message)
    }

    BlankUserNameException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankUserNameException(Throwable cause) {
        super(cause)
    }
}
