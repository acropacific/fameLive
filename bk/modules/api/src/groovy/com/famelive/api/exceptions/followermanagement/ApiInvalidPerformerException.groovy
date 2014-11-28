package com.famelive.api.exceptions.followermanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiInvalidPerformerException extends ApiValidationException {

    ApiInvalidPerformerException() {
    }

    ApiInvalidPerformerException(String message) {
        super(message)
    }

    ApiInvalidPerformerException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidPerformerException(Throwable cause) {
        super(cause)
    }
}
