package com.famelive.api.command.slotmanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.slotmanagement.FetchAllGenreCommand
import grails.validation.Validateable

@Validateable
class ApiFetchGenreCommand extends ApiAuthenticationTokenCommand {

    @Override
    FetchAllGenreCommand toRequestCommand() {
        return new FetchAllGenreCommand(id: this?.id)
    }
}
