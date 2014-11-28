package com.famelive.common.exceptions.followermanagement

import com.famelive.common.exceptions.ValidationException

class InvalidPerformerException extends ValidationException {

    InvalidPerformerException() {}

    InvalidPerformerException(String message) {
        super(message)
    }

    InvalidPerformerException(String message, Throwable cause) {
        super(message, cause)
    }

    InvalidPerformerException(Throwable cause) {
        super(cause)
    }
}
