package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.FetchAllGenreCommand
import com.famelive.common.command.usernamagement.UserSearchCommand
import grails.validation.Validateable

@Validateable
class AdminFetchAllGenreCommand extends AdminAuthenticationCommand {

    @Override
    FetchAllGenreCommand toRequestCommand() {
        new FetchAllGenreCommand(id: this?.id)
    }

    UserSearchCommand toUserSearchCommandCommand() {
        new UserSearchCommand(id: this?.id)
    }
}
