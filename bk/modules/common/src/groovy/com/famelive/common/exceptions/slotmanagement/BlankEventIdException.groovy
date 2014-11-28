package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankEventIdException extends ValidationException {

    BlankEventIdException() {}

    BlankEventIdException(String message) {
        super(message)
    }

    BlankEventIdException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEventIdException(Throwable cause) {
        super(cause)
    }
}
