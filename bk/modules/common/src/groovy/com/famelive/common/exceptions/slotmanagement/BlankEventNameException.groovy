package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankEventNameException extends ValidationException {

    BlankEventNameException() {}

    BlankEventNameException(String message) {
        super(message)
    }

    BlankEventNameException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEventNameException(Throwable cause) {
        super(cause)
    }
}
