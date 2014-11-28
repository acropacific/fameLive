package com.famelive.admin.command.usermanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.usernamagement.FetchUserProfileCommand

class AdminFetchUserProfileCommand extends AdminAuthenticationCommand {

    @Override
    FetchUserProfileCommand toRequestCommand() {
        return new FetchUserProfileCommand(id: this?.id)
    }
}
