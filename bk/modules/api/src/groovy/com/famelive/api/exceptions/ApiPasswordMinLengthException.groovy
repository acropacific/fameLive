package com.famelive.api.exceptions

class ApiPasswordMinLengthException extends ApiValidationException {

    ApiPasswordMinLengthException() {
    }

    ApiPasswordMinLengthException(String message) {
        super(message)
    }

    ApiPasswordMinLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiPasswordMinLengthException(Throwable cause) {
        super(cause)
    }
}
