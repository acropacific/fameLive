package com.famelive.common.exceptions

class MobileNumberInvalidException extends ValidationException {

    MobileNumberInvalidException() {}

    MobileNumberInvalidException(String message) {
        super(message)
    }

    MobileNumberInvalidException(String message, Throwable cause) {
        super(message, cause)
    }

    MobileNumberInvalidException(Throwable cause) {
        super(cause)
    }
}
