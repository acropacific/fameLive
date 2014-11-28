package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankEventGenreException extends ApiValidationException {

    ApiBlankEventGenreException() {
    }

    ApiBlankEventGenreException(String message) {
        super(message)
    }

    ApiBlankEventGenreException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankEventGenreException(Throwable cause) {
        super(cause)
    }
}
