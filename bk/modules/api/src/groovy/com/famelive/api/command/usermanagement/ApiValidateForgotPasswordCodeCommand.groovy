package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.common.command.usernamagement.ValidateForgotPasswordCodeCommand
import grails.validation.Validateable

@Validateable
class ApiValidateForgotPasswordCodeCommand extends ApiRequestCommand {

    String email
    String code

    static constraints = {
        email nullable: true
        code nullable: true
    }

    @Override
    ValidateForgotPasswordCodeCommand toRequestCommand() {
        return new ValidateForgotPasswordCodeCommand(this.properties)
    }
}
