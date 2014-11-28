package com.famelive.web.exceptions

class WebValidationException extends WebException {

    WebValidationException() {}

    WebValidationException(String message) {
        super(message)
    }

    WebValidationException(String message, Throwable cause) {
        super(message, cause)
    }

    WebValidationException(Throwable cause) {
        super(cause)
    }
}
