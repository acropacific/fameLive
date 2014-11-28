package com.famelive.common.exceptions

class BlankSocialAccountException extends ValidationException {

    BlankSocialAccountException() {}

    BlankSocialAccountException(String message) {
        super(message)
    }

    BlankSocialAccountException(String message, Throwable cause) {
        super(message, cause)
    }

    BlankSocialAccountException(Throwable cause) {
        super(cause)
    }
}
