package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankGenreNameException extends ValidationException {

    BlankGenreNameException() {}

    BlankGenreNameException(String message) {
        super(message)
    }

    BlankGenreNameException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankGenreNameException(Throwable cause) {
        super(cause)
    }
}
