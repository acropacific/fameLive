package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.ChangeEmailCommand
import com.famelive.common.command.usernamagement.ChangePasswordCommand
import grails.validation.Validateable

@Validateable
class ApiChangeEmailCommand extends ApiAuthenticationTokenCommand {

    String email

    static constraints = {
        email nullable: true
    }

    @Override
    ChangeEmailCommand toRequestCommand() {
        return new ChangeEmailCommand(id: this?.id, email: this?.email)
    }
}
