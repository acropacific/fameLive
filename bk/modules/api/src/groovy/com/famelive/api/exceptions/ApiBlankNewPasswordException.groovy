package com.famelive.api.exceptions

class ApiBlankNewPasswordException extends ApiValidationException {

    ApiBlankNewPasswordException() {
    }

    ApiBlankNewPasswordException(String message) {
        super(message)
    }

    ApiBlankNewPasswordException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankNewPasswordException(Throwable cause) {
        super(cause)
    }
}
