package com.famelive.common.exceptions

class ValidationException extends CommonException {

    ValidationException() {}

    ValidationException(String message) {
        super(message)
    }

    ValidationException(String message, Throwable cause) {
        super(message, cause)
    }

    ValidationException(Throwable cause) {
        super(cause)
    }

}
