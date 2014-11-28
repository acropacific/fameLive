package com.famelive.common.exceptions

class BlankMobileException extends ValidationException {

    BlankMobileException() {}

    BlankMobileException(String message) {
        super(message)
    }

    BlankMobileException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankMobileException(Throwable cause) {
        super(cause)
    }
}
