package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.FetchUserProfileCommand
import grails.validation.Validateable

@Validateable
class ApiFetchUserProfileCommand extends ApiAuthenticationTokenCommand {

    @Override
    FetchUserProfileCommand toRequestCommand() {
        return new FetchUserProfileCommand(id: this.id)
    }
}
