package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class EventNotFoundException extends ValidationException {

    EventNotFoundException() {}

    EventNotFoundException(String message) {
        super(message)
    }

    EventNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    EventNotFoundException(Throwable cause) {
        super(cause)
    }
}
