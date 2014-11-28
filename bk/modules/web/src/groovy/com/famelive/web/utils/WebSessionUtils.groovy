package com.famelive.web.utils

class WebSessionUtils {

    static def springSecurityService

    public static Long fetchCurrentUserId() {
        springSecurityService?.currentUser?.id as Long
    }
}
