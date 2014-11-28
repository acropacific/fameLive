package com.famelive.common.exceptions

class NewPasswordConfirmPasswordNotSameException extends ValidationException {

    NewPasswordConfirmPasswordNotSameException() {}

    NewPasswordConfirmPasswordNotSameException(String message) {
        super(message)
    }

    NewPasswordConfirmPasswordNotSameException(String message, Throwable cause) {
        super(message, cause)
    }

    NewPasswordConfirmPasswordNotSameException(Throwable cause) {
        super(cause)
    }
}
