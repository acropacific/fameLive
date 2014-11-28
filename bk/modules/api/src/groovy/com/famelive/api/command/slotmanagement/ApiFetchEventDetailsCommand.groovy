package com.famelive.api.command.slotmanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.common.command.slotmanagement.EventDetailsCommand
import grails.validation.Validateable

@Validateable
class ApiFetchEventDetailsCommand extends ApiRequestCommand {
    String eventID
    static constraints = {
        eventID nullable: true
    }

    @Override
    EventDetailsCommand toRequestCommand() {
        return new EventDetailsCommand(eventID: this?.eventID)
    }
}
