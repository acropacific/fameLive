package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class EventNameMaxLengthException extends ValidationException {

    EventNameMaxLengthException() {}

    EventNameMaxLengthException(String message) {
        super(message)
    }

    EventNameMaxLengthException(String message, Throwable cause) {
        super(message, cause)
    }

    EventNameMaxLengthException(Throwable cause) {
        super(cause)
    }
}
