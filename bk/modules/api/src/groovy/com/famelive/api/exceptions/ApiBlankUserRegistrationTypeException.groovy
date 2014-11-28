package com.famelive.api.exceptions

class ApiBlankUserRegistrationTypeException extends ApiValidationException {

    ApiBlankUserRegistrationTypeException() {
    }

    ApiBlankUserRegistrationTypeException(String message) {
        super(message)
    }

    ApiBlankUserRegistrationTypeException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankUserRegistrationTypeException(Throwable cause) {
        super(cause)
    }
}
