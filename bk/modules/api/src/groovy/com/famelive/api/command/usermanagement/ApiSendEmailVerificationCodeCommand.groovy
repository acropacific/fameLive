package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.SendEmailVerificationCodeCommand
import grails.validation.Validateable

@Validateable
class ApiSendEmailVerificationCodeCommand extends ApiAuthenticationTokenCommand {


    static constraints = {
    }

    @Override
    SendEmailVerificationCodeCommand toRequestCommand() {
        return new SendEmailVerificationCodeCommand(id: this?.id)
    }
}
