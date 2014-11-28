package com.famelive.common.exceptions

class EmailNotFoundException extends ValidationException {

    EmailNotFoundException() {}

    EmailNotFoundException(String message) {
        super(message)
    }

    EmailNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    EmailNotFoundException(Throwable cause) {
        super(cause)
    }
}
