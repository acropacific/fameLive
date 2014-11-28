package com.famelive.api.exceptions

class ApiBlankSocialAccountException extends ApiValidationException {

    ApiBlankSocialAccountException() {
    }

    ApiBlankSocialAccountException(String message) {
        super(message)
    }

    ApiBlankSocialAccountException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiBlankSocialAccountException(Throwable cause) {
        super(cause)
    }
}
