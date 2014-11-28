package com.famelive.api.exceptions

class ApiBlankMobileException extends ApiValidationException {

    ApiBlankMobileException() {
    }

    ApiBlankMobileException(String message) {
        super(message)
    }

    ApiBlankMobileException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankMobileException(Throwable cause) {
        super(cause)
    }
}
