package com.famelive.api.exceptions

class ApiUserNameMaxSizeException extends ApiValidationException {

    ApiUserNameMaxSizeException() {
    }

    ApiUserNameMaxSizeException(String message) {
        super(message)
    }

    ApiUserNameMaxSizeException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiUserNameMaxSizeException(Throwable cause) {
        super(cause)
    }
}
