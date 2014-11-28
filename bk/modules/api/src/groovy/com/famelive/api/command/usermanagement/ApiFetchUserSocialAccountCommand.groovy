package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.FetchUserProfileCommand
import com.famelive.common.command.usernamagement.FetchUserSocialAccountCommand
import grails.validation.Validateable

@Validateable
class ApiFetchUserSocialAccountCommand extends ApiAuthenticationTokenCommand {

    @Override
    FetchUserSocialAccountCommand toRequestCommand() {
        return new FetchUserSocialAccountCommand(id: this.id)
    }
}
