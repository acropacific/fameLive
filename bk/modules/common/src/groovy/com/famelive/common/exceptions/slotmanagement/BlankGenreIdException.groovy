package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class BlankGenreIdException extends ValidationException {

    BlankGenreIdException() {}

    BlankGenreIdException(String message) {
        super(message)
    }

    BlankGenreIdException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankGenreIdException(Throwable cause) {
        super(cause)
    }
}
