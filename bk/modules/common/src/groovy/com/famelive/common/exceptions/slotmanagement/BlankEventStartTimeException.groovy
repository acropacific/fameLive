package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankEventStartTimeException extends ValidationException {

    BlankEventStartTimeException() {}

    BlankEventStartTimeException(String message) {
        super(message)
    }

    BlankEventStartTimeException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEventStartTimeException(Throwable cause) {
        super(cause)
    }
}
