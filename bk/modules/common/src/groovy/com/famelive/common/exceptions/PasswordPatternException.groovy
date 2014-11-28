package com.famelive.common.exceptions

class PasswordPatternException extends ValidationException {

    PasswordPatternException() {}

    PasswordPatternException(String message) {
        super(message)
    }

    PasswordPatternException(String message, Throwable cause) {
        super(message, cause)
    }

    PasswordPatternException(Throwable cause) {
        super(cause)
    }
}
