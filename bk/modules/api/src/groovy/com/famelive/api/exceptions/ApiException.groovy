package com.famelive.api.exceptions

class ApiException extends RuntimeException {

    String message = ""

    ApiException() {}

    ApiException(String message) {
        super(message)
    }

    ApiException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiException(Throwable cause) {
        super(cause)
    }

}
