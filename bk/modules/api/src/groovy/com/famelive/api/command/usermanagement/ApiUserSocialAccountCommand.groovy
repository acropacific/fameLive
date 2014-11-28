package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.api.enums.ApiSocialAccount
import com.famelive.common.command.usernamagement.UserSocialAccountCommand
import grails.validation.Validateable

@Validateable
class ApiUserSocialAccountCommand extends ApiAuthenticationTokenCommand {
    ApiSocialAccount socialAccount
    String token

    static constraints = {
        socialAccount nullable: true
        token nullable: true
    }

    @Override
    UserSocialAccountCommand toRequestCommand() {
        return new UserSocialAccountCommand(id: this?.id, socialAccount: this?.socialAccount?.socialAccount, token: this?.token)
    }
}
