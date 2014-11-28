package com.famelive.common.exceptions.followermanagement

import com.famelive.common.exceptions.ValidationException

class InvalidFollowDetailException extends ValidationException {

    InvalidFollowDetailException() {}

    InvalidFollowDetailException(String message) {
        super(message)
    }

    InvalidFollowDetailException(String message, Throwable cause) {
        super(message, cause)
    }

    InvalidFollowDetailException(Throwable cause) {
        super(cause)
    }
}
