package com.famelive.api.exceptions

class ApiUniqueFameNameException extends ApiValidationException {

    ApiUniqueFameNameException() {
    }

    ApiUniqueFameNameException(String message) {
        super(message)
    }

    ApiUniqueFameNameException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiUniqueFameNameException(Throwable cause) {
        super(cause)
    }
}
