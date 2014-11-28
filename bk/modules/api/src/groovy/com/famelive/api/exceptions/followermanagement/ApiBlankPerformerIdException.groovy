package com.famelive.api.exceptions.followermanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiBlankPerformerIdException extends ApiValidationException {

    ApiBlankPerformerIdException() {
    }

    ApiBlankPerformerIdException(String message) {
        super(message)
    }

    ApiBlankPerformerIdException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankPerformerIdException(Throwable cause) {
        super(cause)
    }
}
