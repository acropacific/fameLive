package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankEventDurationException extends ValidationException {

    BlankEventDurationException() {}

    BlankEventDurationException(String message) {
        super(message)
    }

    BlankEventDurationException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEventDurationException(Throwable cause) {
        super(cause)
    }
}
