package com.famelive.api.exceptions

class ApiMobileNumberLengthException extends ApiValidationException {

    ApiMobileNumberLengthException() {
    }

    ApiMobileNumberLengthException(String message) {
        super(message)
    }

    ApiMobileNumberLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiMobileNumberLengthException(Throwable cause) {
        super(cause)
    }
}
