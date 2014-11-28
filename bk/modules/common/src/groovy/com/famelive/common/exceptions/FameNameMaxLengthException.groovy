package com.famelive.common.exceptions

class FameNameMaxLengthException extends ValidationException {

    FameNameMaxLengthException() {}

    FameNameMaxLengthException(String message) {
        super(message)
    }

    FameNameMaxLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    FameNameMaxLengthException(Throwable cause) {
        super(cause)
    }
}
