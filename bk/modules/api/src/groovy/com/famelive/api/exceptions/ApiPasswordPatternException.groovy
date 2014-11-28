package com.famelive.api.exceptions

class ApiPasswordPatternException extends ApiValidationException {

    ApiPasswordPatternException() {
    }

    ApiPasswordPatternException(String message) {
        super(message)
    }

    ApiPasswordPatternException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiPasswordPatternException(Throwable cause) {
        super(cause)
    }
}
