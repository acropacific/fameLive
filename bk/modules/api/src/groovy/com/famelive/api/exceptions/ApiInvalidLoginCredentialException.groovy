package com.famelive.api.exceptions

class ApiInvalidLoginCredentialException extends ApiValidationException {

    ApiInvalidLoginCredentialException() {
    }

    ApiInvalidLoginCredentialException(String message) {
        super(message)
    }

    ApiInvalidLoginCredentialException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidLoginCredentialException(Throwable cause) {
        super(cause)
    }
}
