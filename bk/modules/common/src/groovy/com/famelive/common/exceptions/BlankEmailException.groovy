package com.famelive.common.exceptions

class BlankEmailException extends ValidationException {

    BlankEmailException() {}

    BlankEmailException(String message) {
        super(message)
    }

    BlankEmailException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEmailException(Throwable cause) {
        super(cause)
    }
}
