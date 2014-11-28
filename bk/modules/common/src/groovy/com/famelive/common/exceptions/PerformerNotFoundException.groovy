package com.famelive.common.exceptions

class PerformerNotFoundException extends ValidationException {

    PerformerNotFoundException() {}

    PerformerNotFoundException(String message) {
        super(message)
    }

    PerformerNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    PerformerNotFoundException(Throwable cause) {
        super(cause)
    }
}
