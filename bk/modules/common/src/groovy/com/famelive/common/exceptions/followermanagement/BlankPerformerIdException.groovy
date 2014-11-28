package com.famelive.common.exceptions.followermanagement

import com.famelive.common.exceptions.ValidationException

class BlankPerformerIdException extends ValidationException {

    BlankPerformerIdException() {}

    BlankPerformerIdException(String message) {
        super(message)
    }

    BlankPerformerIdException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankPerformerIdException(Throwable cause) {
        super(cause)
    }
}
