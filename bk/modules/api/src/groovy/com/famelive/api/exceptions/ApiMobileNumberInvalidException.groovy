package com.famelive.api.exceptions

class ApiMobileNumberInvalidException extends ApiValidationException {

    ApiMobileNumberInvalidException() {
    }

    ApiMobileNumberInvalidException(String message) {
        super(message)
    }

    ApiMobileNumberInvalidException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiMobileNumberInvalidException(Throwable cause) {
        super(cause)
    }
}
