package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.ChangeEmailCommand
import com.famelive.common.command.usernamagement.CheckUserAccountCommand
import grails.validation.Validateable

@Validateable
class ApiCheckUserAccountCommand extends ApiAuthenticationTokenCommand {

    static constraints = {
    }

    @Override
    CheckUserAccountCommand toRequestCommand() {
        return new CheckUserAccountCommand(id: this?.id)
    }
}
