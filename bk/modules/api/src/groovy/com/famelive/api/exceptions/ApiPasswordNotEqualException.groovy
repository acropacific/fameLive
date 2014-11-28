package com.famelive.api.exceptions

class ApiPasswordNotEqualException extends ApiException {

    ApiPasswordNotEqualException() {
    }

    ApiPasswordNotEqualException(String message) {
        super(message)
    }

    ApiPasswordNotEqualException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiPasswordNotEqualException(Throwable cause) {
        super(cause)
    }

}
