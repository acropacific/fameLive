package com.famelive.common.exceptions

class BlankUserTypeException extends ValidationException {

    BlankUserTypeException() {}

    BlankUserTypeException(String message) {
        super(message)
    }

    BlankUserTypeException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankUserTypeException(Throwable cause) {
        super(cause)
    }
}
