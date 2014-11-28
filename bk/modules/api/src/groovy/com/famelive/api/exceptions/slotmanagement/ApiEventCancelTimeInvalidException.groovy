package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiEventCancelTimeInvalidException extends ApiValidationException {

    ApiEventCancelTimeInvalidException() {
    }

    ApiEventCancelTimeInvalidException(String message) {
        super(message)
    }

    ApiEventCancelTimeInvalidException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiEventCancelTimeInvalidException(Throwable cause) {
        super(cause)
    }
}
