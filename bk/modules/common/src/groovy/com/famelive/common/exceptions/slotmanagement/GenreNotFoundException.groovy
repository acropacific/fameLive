package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class GenreNotFoundException extends ValidationException {

    GenreNotFoundException() {}

    GenreNotFoundException(String message) {
        super(message)
    }

    GenreNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    GenreNotFoundException(Throwable cause) {
        super(cause)
    }
}
