package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankEventImageNameException extends ValidationException {

    BlankEventImageNameException() {}

    BlankEventImageNameException(String message) {
        super(message)
    }

    BlankEventImageNameException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEventImageNameException(Throwable cause) {
        super(cause)
    }
}
