package com.famelive.common.exceptions

class UnauthorizedEmailException extends ValidationException {

    UnauthorizedEmailException() {}

    UnauthorizedEmailException(String message) {
        super(message)
    }

    UnauthorizedEmailException(String message, Throwable cause) {
        super(message, cause)
    }

    UnauthorizedEmailException(Throwable cause) {
        super(cause)
    }
}
