package com.famelive.common.exceptions

class UniqueFameNameException extends ValidationException {

    UniqueFameNameException() {}

    UniqueFameNameException(String message) {
        super(message)
    }

    UniqueFameNameException(String message, Throwable cause) {
        super(message, cause)
    }

    UniqueFameNameException(Throwable cause) {
        super(cause)
    }
}
