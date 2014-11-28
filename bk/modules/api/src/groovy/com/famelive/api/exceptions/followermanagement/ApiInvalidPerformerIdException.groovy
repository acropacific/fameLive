package com.famelive.api.exceptions.followermanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiInvalidPerformerIdException extends ApiValidationException {

    ApiInvalidPerformerIdException() {
    }

    ApiInvalidPerformerIdException(String message) {
        super(message)
    }

    ApiInvalidPerformerIdException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidPerformerIdException(Throwable cause) {
        super(cause)
    }
}
