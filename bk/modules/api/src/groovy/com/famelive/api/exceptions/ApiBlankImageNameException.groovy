package com.famelive.api.exceptions

class ApiBlankImageNameException extends ApiValidationException {

    ApiBlankImageNameException() {
    }

    ApiBlankImageNameException(String message) {
        super(message)
    }

    ApiBlankImageNameException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankImageNameException(Throwable cause) {
        super(cause)
    }
}
