package com.famelive.api.exceptions

class ApiUserInvalidOperationException extends ApiValidationException {

    ApiUserInvalidOperationException() {
    }

    ApiUserInvalidOperationException(String message) {
        super(message)
    }

    ApiUserInvalidOperationException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiUserInvalidOperationException(Throwable cause) {
        super(cause)
    }
}
