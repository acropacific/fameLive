package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankGenreStatusException extends ValidationException {

    BlankGenreStatusException() {}

    BlankGenreStatusException(String message) {
        super(message)
    }

    BlankGenreStatusException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankGenreStatusException(Throwable cause) {
        super(cause)
    }
}
