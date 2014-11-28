package com.famelive.api.exceptions

class ApiBlankFameNameException extends ApiValidationException {

    ApiBlankFameNameException() {
    }

    ApiBlankFameNameException(String message) {
        super(message)
    }

    ApiBlankFameNameException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankFameNameException(Throwable cause) {
        super(cause)
    }
}
