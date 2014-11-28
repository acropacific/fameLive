package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.FetchEventCommand
import com.famelive.common.command.usernamagement.UserSearchCommand
import grails.validation.Validateable

@Validateable
class AdminFetchEventCommand extends AdminAuthenticationCommand {

    Long eventId

    static constraints = {
        eventId nullable: true
    }

    @Override
    FetchEventCommand toRequestCommand() {
        new FetchEventCommand(id: this?.id, eventId: this?.eventId)
    }

    UserSearchCommand toUserSearchCommandCommand() {
        new UserSearchCommand(id: this?.id)
    }
}
