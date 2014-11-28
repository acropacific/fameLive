package com.famelive.web.exceptions

class WebException extends RuntimeException {

    WebException() {}

    WebException(String message) {
        super(message)
    }

    WebException(String message, Throwable cause) {
        super(message, cause)
    }

    WebException(Throwable cause) {
        super(cause)
    }
}
