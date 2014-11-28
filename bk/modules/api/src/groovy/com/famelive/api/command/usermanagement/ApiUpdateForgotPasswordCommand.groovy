package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.common.command.usernamagement.UpdateForgotPasswordCommand
import grails.validation.Validateable

@Validateable
class ApiUpdateForgotPasswordCommand extends ApiRequestCommand {

    String email
    String password
    String confirmPassword
    String sessionEmail

    static constraints = {
        email nullable: true
        password nullable: true
        confirmPassword nulllable: true
        sessionEmail nullable: true
    }

    @Override
    UpdateForgotPasswordCommand toRequestCommand() {
        return new UpdateForgotPasswordCommand(email: this?.email, password: this?.password, confirmPassword: this?.confirmPassword, sessionEmail: this?.sessionEmail)
    }
}
