package com.famelive.common.exceptions

class BlankNewPasswordException extends ValidationException {

    BlankNewPasswordException() {}

    BlankNewPasswordException(String message) {
        super(message)
    }

    BlankNewPasswordException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankNewPasswordException(Throwable cause) {
        super(cause)
    }
}
