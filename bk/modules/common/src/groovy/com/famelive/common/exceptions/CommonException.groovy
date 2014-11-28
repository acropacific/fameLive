package com.famelive.common.exceptions

class CommonException extends RuntimeException {
    String message = ""

    CommonException() {}

    CommonException(String message) {
        super(message)
    }

    CommonException(String message, Throwable cause) {
        super(message, cause)
    }

    CommonException(Throwable cause) {
        super(cause)
    }
}
