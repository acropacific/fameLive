package com.famelive.admin.command.usermanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.FetchEventsCommand
import com.famelive.common.command.usernamagement.FetchUserProfileCommand
import grails.validation.Validateable

@Validateable
class AdminUserProfileCommand extends AdminAuthenticationCommand {

    Long userId

    @Override
    FetchUserProfileCommand toRequestCommand() {
        return new FetchUserProfileCommand(id: this?.userId)
    }

    FetchEventsCommand toFetchEventsCommand() {
        return new FetchEventsCommand(id: this?.userId)
    }

}
