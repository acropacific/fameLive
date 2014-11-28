package com.famelive.common.exceptions

class BlankUserRegistrationTypeException extends ValidationException {

    BlankUserRegistrationTypeException() {}

    BlankUserRegistrationTypeException(String message) {
        super(message)
    }

    BlankUserRegistrationTypeException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankUserRegistrationTypeException(Throwable cause) {
        super(cause)
    }
}
