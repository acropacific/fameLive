package com.famelive.api.exceptions

class ApiNewPasswordConfirmPasswordNotSameException extends ApiValidationException {

    ApiNewPasswordConfirmPasswordNotSameException() {
    }

    ApiNewPasswordConfirmPasswordNotSameException(String message) {
        super(message)
    }

    ApiNewPasswordConfirmPasswordNotSameException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiNewPasswordConfirmPasswordNotSameException(Throwable cause) {
        super(cause)
    }
}
