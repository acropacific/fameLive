package com.famelive.api.exceptions

class ApiEmailNotFoundException extends ApiValidationException {

    ApiEmailNotFoundException() {
    }

    ApiEmailNotFoundException(String message) {
        super(message)
    }

    ApiEmailNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiEmailNotFoundException(Throwable cause) {
        super(cause)
    }
}
