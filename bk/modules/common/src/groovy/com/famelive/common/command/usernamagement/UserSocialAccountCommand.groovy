package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.enums.usermanagement.SocialAccount
import com.famelive.common.exceptions.BlankSocialAccountException
import grails.validation.Validateable

@Validateable
class UserSocialAccountCommand extends AuthenticationTokenCommand {
    SocialAccount socialAccount
    String token

    static constraints = {
        socialAccount nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankSocialAccountException()
            }
        }
        token nullable: true
    }
}