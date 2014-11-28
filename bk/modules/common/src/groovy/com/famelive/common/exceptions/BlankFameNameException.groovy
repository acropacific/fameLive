package com.famelive.common.exceptions

class BlankFameNameException extends ValidationException {

    BlankFameNameException() {}

    BlankFameNameException(String message) {
        super(message)
    }

    BlankFameNameException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankFameNameException(Throwable cause) {
        super(cause)
    }
}
