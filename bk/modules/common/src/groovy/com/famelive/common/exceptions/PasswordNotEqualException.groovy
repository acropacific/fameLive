package com.famelive.common.exceptions

class PasswordNotEqualException extends CommonException {

    PasswordNotEqualException() {}

    PasswordNotEqualException(String message) {
        super(message)
    }

    PasswordNotEqualException(String message, Throwable cause) {
        super(message, cause)
    }

    PasswordNotEqualException(Throwable cause) {
        super(cause)
    }
}
