package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.FetchPerformerProfileCommand
import grails.validation.Validateable

@Validateable
class ApiFetchPerformerProfileCommand extends ApiAuthenticationTokenCommand {

    String fameId

    @Override
    FetchPerformerProfileCommand toRequestCommand() {
        return new FetchPerformerProfileCommand(id: this.id, fameId: this.fameId)
    }
}
