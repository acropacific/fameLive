package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.ChangePasswordCommand
import grails.validation.Validateable

@Validateable
class ApiChangePasswordCommand extends ApiAuthenticationTokenCommand {
    String email
    String password
    String newPassword
    String confirmPassword

    static constraints = {
        password nullable: true
        newPassword nullable: true
        confirmPassword nullable: true
    }

    @Override
    ChangePasswordCommand toRequestCommand() {
        return new ChangePasswordCommand(id: this?.id, email: this?.email, password: this?.password, newPassword: this?.newPassword, confirmPassword: this?.confirmPassword)
    }
}
