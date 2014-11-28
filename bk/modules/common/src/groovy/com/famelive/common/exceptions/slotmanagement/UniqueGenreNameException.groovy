package com.famelive.common.exceptions.slotmanagement

import com.famelive.common.exceptions.ValidationException

class UniqueGenreNameException extends ValidationException {

    UniqueGenreNameException() {}

    UniqueGenreNameException(String message) {
        super(message)
    }

    UniqueGenreNameException(String message, Throwable cause) {
        super(message, cause)
    }

    UniqueGenreNameException(Throwable cause) {
        super(cause)
    }
}
