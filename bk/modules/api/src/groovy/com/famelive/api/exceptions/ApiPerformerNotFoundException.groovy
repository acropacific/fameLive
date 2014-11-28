package com.famelive.api.exceptions

class ApiPerformerNotFoundException extends ApiValidationException {

    ApiPerformerNotFoundException() {
    }

    ApiPerformerNotFoundException(String message) {
        super(message)
    }

    ApiPerformerNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiPerformerNotFoundException(Throwable cause) {
        super(cause)
    }
}
