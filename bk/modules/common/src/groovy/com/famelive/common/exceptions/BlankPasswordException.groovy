package com.famelive.common.exceptions

class BlankPasswordException extends ValidationException {

    BlankPasswordException() {}

    BlankPasswordException(String message) {
        super(message)
    }

    BlankPasswordException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankPasswordException(Throwable cause) {
        super(cause)
    }
}
