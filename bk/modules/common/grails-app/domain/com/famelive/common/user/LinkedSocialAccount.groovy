package com.famelive.common.user

import com.famelive.common.enums.usermanagement.SocialAccount

class LinkedSocialAccount {

    SocialAccount socialAccount
    Boolean isActive = true
    String token
    User user

    static constraints = {
        socialAccount nullable: false
        token nullable: true
    }

}
