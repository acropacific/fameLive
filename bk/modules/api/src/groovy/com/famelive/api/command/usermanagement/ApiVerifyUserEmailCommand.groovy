package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.VerifyUserEmailCommand
import grails.validation.Validateable

@Validateable
class ApiVerifyUserEmailCommand extends ApiAuthenticationTokenCommand {

    String verificationToken

    static constraints = {
    }

    @Override
    VerifyUserEmailCommand toRequestCommand() {
        return new VerifyUserEmailCommand(id: this?.id, verificationToken: this?.verificationToken)
    }
}
