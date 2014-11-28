package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class EventDescriptionMaxLengthException extends ValidationException {

    EventDescriptionMaxLengthException() {}

    EventDescriptionMaxLengthException(String message) {
        super(message)
    }

    EventDescriptionMaxLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    EventDescriptionMaxLengthException(Throwable cause) {
        super(cause)
    }
}
