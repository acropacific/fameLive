package com.famelive.api.command.slotmanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.slotmanagement.EventImageCommand
import grails.validation.Validateable

@Validateable
class ApiEventImageCommand extends ApiAuthenticationTokenCommand {
    String eventId
    String imageName

    static constraints = {
        eventId nullable: true
        imageName nullable: true
    }

    @Override
    EventImageCommand toRequestCommand() {
        return new EventImageCommand(id: this?.id, eventId: this?.eventId, imageName: this?.imageName)
    }
}
