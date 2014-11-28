package com.famelive.admin.util

class AdminSessionUtils {

    static def springSecurityService

    public static Long fetchCurrentUserId() {
        springSecurityService?.currentUser?.id as Long
    }
}
