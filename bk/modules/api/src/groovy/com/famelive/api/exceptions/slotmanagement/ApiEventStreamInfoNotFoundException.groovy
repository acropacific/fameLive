package com.famelive.api.exceptions.slotmanagement

import com.famelive.api.exceptions.ApiException

/**
 * Created by anil on 3/11/14.
 */
class ApiEventStreamInfoNotFoundException extends ApiException {

    ApiEventStreamInfoNotFoundException() {
    }

    ApiEventStreamInfoNotFoundException(String message) {
        super(message)
    }

    ApiEventStreamInfoNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiEventStreamInfoNotFoundException(Throwable cause) {
        super(cause)
    }

}
