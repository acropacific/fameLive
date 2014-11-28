package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.CommonException

/**
 * Created by anil on 3/11/14.
 */
class EventStreamInfoNotFoundException extends CommonException {

    EventStreamInfoNotFoundException() {}

    EventStreamInfoNotFoundException(String message) {
        super(message)
    }

    EventStreamInfoNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    EventStreamInfoNotFoundException(Throwable cause) {
        super(cause)
    }
}
