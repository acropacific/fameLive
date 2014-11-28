package com.famelive.common.exceptions

class InvalidTokenException extends CommonException {

    InvalidTokenException() {}

    InvalidTokenException(String message) {
        super(message)
    }

    InvalidTokenException(String message, Throwable cause) {
        super(message, cause)
    }

    InvalidTokenException(Throwable cause) {
        super(cause)
    }

}
