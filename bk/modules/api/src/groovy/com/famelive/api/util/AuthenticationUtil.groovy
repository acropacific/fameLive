package com.famelive.api.util

import com.famelive.common.user.User

/**
 * Created by anil on 25/8/14.
 */
class AuthenticationUtil {
    static User fetchUserByUsername(String username) {
        User user = User.findByEmail(username)
        /*if (user == null) {
            user = User.findByUsername(username)
        }
        if (user == null) {
            user = User.findByMobile(username)
        }*/
        return user
    }
}
