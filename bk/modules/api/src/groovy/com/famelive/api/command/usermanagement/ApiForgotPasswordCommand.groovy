package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.common.command.usernamagement.ForgotPasswordCommand
import grails.validation.Validateable

@Validateable
class ApiForgotPasswordCommand extends ApiRequestCommand {

    String email

    static constraints = {
        email nullable: true
    }

    @Override
    ForgotPasswordCommand toRequestCommand() {
        return new ForgotPasswordCommand(this.properties)
    }
}
