package com.famelive.api.exceptions

class ApiBlankUserTypeException extends ApiValidationException {

    ApiBlankUserTypeException() {
    }

    ApiBlankUserTypeException(String message) {
        super(message)
    }

    ApiBlankUserTypeException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankUserTypeException(Throwable cause) {
        super(cause)
    }
}
