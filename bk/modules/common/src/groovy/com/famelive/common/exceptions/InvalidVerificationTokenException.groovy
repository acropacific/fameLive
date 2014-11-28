package com.famelive.common.exceptions

class InvalidVerificationTokenException extends ValidationException {

    InvalidVerificationTokenException() {}

    InvalidVerificationTokenException(String message) {
        super(message)
    }

    InvalidVerificationTokenException(String message, Throwable cause) {
        super(message, cause)
    }

    InvalidVerificationTokenException(Throwable cause) {
        super(cause)
    }
}
