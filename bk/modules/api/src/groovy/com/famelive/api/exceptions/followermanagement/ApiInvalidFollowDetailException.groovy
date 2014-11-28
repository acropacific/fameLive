package com.famelive.api.exceptions.followermanagement

import com.famelive.api.exceptions.ApiValidationException

class ApiInvalidFollowDetailException extends ApiValidationException {

    ApiInvalidFollowDetailException() {
    }

    ApiInvalidFollowDetailException(String message) {
        super(message)
    }

    ApiInvalidFollowDetailException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidFollowDetailException(Throwable cause) {
        super(cause)
    }
}
