package com.famelive.api.command.slotmanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.slotmanagement.CancelEventCommand
import grails.validation.Validateable

@Validateable
class ApiCancelEventCommand extends ApiAuthenticationTokenCommand {
    String eventId

    static constraints = {
        eventId nullable: true
    }

    @Override
    CancelEventCommand toRequestCommand() {
        return new CancelEventCommand(id: this?.id, eventId: this?.eventId)
    }
}
