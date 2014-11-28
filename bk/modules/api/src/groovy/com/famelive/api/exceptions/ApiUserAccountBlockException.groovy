package com.famelive.api.exceptions

class ApiUserAccountBlockException extends ApiValidationException {

    ApiUserAccountBlockException() {
    }

    ApiUserAccountBlockException(String message) {
        super(message)
    }

    ApiUserAccountBlockException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiUserAccountBlockException(Throwable cause) {
        super(cause)
    }
}
