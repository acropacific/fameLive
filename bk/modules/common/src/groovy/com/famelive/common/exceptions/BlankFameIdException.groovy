package com.famelive.common.exceptions

class BlankFameIdException extends ValidationException {

    BlankFameIdException() {}

    BlankFameIdException(String message) {
        super(message)
    }

    BlankFameIdException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankFameIdException(Throwable cause) {
        super(cause)
    }
}
