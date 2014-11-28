package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class EventCancelTimeInvalidException extends ValidationException {

    EventCancelTimeInvalidException() {}

    EventCancelTimeInvalidException(String message) {
        super(message)
    }

    EventCancelTimeInvalidException(String message, Throwable cause) {
        super(message, cause)
    }

    EventCancelTimeInvalidException(Throwable cause) {
        super(cause)
    }
}
