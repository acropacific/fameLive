package com.famelive.common.exceptions

class InvalidUserSocialAccountException extends ValidationException {

    InvalidUserSocialAccountException() {}

    InvalidUserSocialAccountException(String message) {
        super(message)
    }

    InvalidUserSocialAccountException(String message, Throwable cause) {
        super(message, cause)
    }

    InvalidUserSocialAccountException(Throwable cause) {
        super(cause)
    }
}
