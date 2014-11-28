package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankEventGenreException extends ValidationException {

    BlankEventGenreException() {}

    BlankEventGenreException(String message) {
        super(message)
    }

    BlankEventGenreException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankEventGenreException(Throwable cause) {
        super(cause)
    }
}
