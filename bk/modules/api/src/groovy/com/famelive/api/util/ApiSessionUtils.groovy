package com.famelive.api.util

import com.famelive.api.exceptions.ApiUserAccountBlockException

class ApiSessionUtils {

    static def springSecurityService

    public static Long fetchCurrentUserId() throws ApiUserAccountBlockException {
        if (springSecurityService?.currentUser?.accountLocked) {
            throw new ApiUserAccountBlockException()
        }
        return springSecurityService?.currentUser?.id as Long
    }
}
