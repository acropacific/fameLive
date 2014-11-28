package com.famelive.common.exceptions

class BlankImageNameException extends ValidationException {

    BlankImageNameException() {}

    BlankImageNameException(String message) {
        super(message)
    }

    BlankImageNameException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankImageNameException(Throwable cause) {
        super(cause)
    }
}
