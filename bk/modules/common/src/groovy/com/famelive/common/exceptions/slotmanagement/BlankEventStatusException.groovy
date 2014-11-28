package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankEventStatusException extends ValidationException {

    BlankEventStatusException() {}

    BlankEventStatusException(String message) {
        super(message)
    }

    BlankEventStatusException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEventStatusException(Throwable cause) {
        super(cause)
    }
}
