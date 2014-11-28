package com.famelive.api.exceptions

class ApiInvalidUserSocialAccountException extends ApiValidationException {

    ApiInvalidUserSocialAccountException() {
    }

    ApiInvalidUserSocialAccountException(String message) {
        super(message)
    }

    ApiInvalidUserSocialAccountException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiInvalidUserSocialAccountException(Throwable cause) {
        super(cause)
    }
}
