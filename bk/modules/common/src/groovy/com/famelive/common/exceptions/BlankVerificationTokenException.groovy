package com.famelive.common.exceptions

class BlankVerificationTokenException extends ValidationException {

    BlankVerificationTokenException() {}

    BlankVerificationTokenException(String message) {
        super(message)
    }

    BlankVerificationTokenException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankVerificationTokenException(Throwable cause) {
        super(cause)
    }
}
