package com.famelive.admin.exception

class AdminException extends RuntimeException {
    String message = ""

    AdminException() {}

    AdminException(String message) {
        super(message)
    }

    AdminException(String message, Throwable cause) {
        super(message, cause)
    }

    AdminException(Throwable cause) {
        super(cause)
    }
}
