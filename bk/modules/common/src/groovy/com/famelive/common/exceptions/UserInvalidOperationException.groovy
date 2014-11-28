package com.famelive.common.exceptions

class UserInvalidOperationException extends ValidationException {

    UserInvalidOperationException() {}

    UserInvalidOperationException(String message) {
        super(message)
    }

    UserInvalidOperationException(String message, Throwable cause) {
        super(message, cause)
    }

    UserInvalidOperationException(Throwable cause) {
        super(cause)
    }
}
