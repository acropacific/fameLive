package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.CancelEventCommand
import grails.validation.Validateable

@Validateable
class AdminCancelEventCommand extends AdminAuthenticationCommand {

    Long eventId

    static constraints = {
        eventId nullable: true
    }

    @Override
    CancelEventCommand toRequestCommand() {
        new CancelEventCommand(id: this?.id, eventId: this?.eventId)
    }
}
