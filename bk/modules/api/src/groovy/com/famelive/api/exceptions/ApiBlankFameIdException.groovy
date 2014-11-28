package com.famelive.api.exceptions

class ApiBlankFameIdException extends ApiValidationException {

    ApiBlankFameIdException() {
    }

    ApiBlankFameIdException(String message) {
        super(message)
    }

    ApiBlankFameIdException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankFameIdException(Throwable cause) {
        super(cause)
    }
}
