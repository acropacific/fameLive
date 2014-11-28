package com.famelive.web.command

import com.famelive.common.command.usernamagement.ChangePasswordCommand
import grails.validation.Validateable

@Validateable
class WebChangePasswordCommand extends WebAuthenticationCommand {

    String email
    String password
    String newPassword
    String confirmPassword

    static constraints = {
        password nullable: true
        newPassword nullable: true
        confirmPassword nullable: true
        email nullable: true
    }

    @Override
    ChangePasswordCommand toRequestCommand() {
        return new ChangePasswordCommand(id: this?.id, email: this?.email, password: this?.password, newPassword: this?.newPassword, confirmPassword: this?.confirmPassword)
    }
}
