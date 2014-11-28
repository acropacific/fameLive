package com.famelive.common.exceptions

class MobileNumberLengthException extends ValidationException {

    MobileNumberLengthException() {}

    MobileNumberLengthException(String message) {
        super(message)
    }

    MobileNumberLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    MobileNumberLengthException(Throwable cause) {
        super(cause)
    }
}
