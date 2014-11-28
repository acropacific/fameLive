package com.famelive.common.exceptions

class UniqueEmailException extends ValidationException {

    UniqueEmailException() {}

    UniqueEmailException(String message) {
        super(message)
    }

    UniqueEmailException(String message, Throwable cause) {
        super(message, cause)
    }

    UniqueEmailException(Throwable cause) {
        super(cause)
    }
}
