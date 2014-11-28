package com.famelive.web.command

import com.famelive.common.command.usernamagement.FetchUserProfileCommand
import grails.validation.Validateable

@Validateable
class WebFetchUserProfileCommand extends WebAuthenticationCommand {

    @Override
    FetchUserProfileCommand toRequestCommand() {
        return new FetchUserProfileCommand(id: this?.id)
    }
}
