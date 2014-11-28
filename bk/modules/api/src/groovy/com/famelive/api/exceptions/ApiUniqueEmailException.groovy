package com.famelive.api.exceptions

class ApiUniqueEmailException extends ApiValidationException {

    ApiUniqueEmailException() {
    }

    ApiUniqueEmailException(String message) {
        super(message)
    }

    ApiUniqueEmailException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiUniqueEmailException(Throwable cause) {
        super(cause)
    }
}
