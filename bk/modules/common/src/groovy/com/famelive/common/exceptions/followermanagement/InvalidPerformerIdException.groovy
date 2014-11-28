package com.famelive.common.exceptions.followermanagement

import com.famelive.common.exceptions.ValidationException

class InvalidPerformerIdException extends ValidationException {

    InvalidPerformerIdException() {}

    InvalidPerformerIdException(String message) {
        super(message)
    }

    InvalidPerformerIdException(String message, Throwable cause) {
        super(message, cause)
    }

    InvalidPerformerIdException(Throwable cause) {
        super(cause)
    }
}
