package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import grails.validation.Validateable

@Validateable
class SendEmailVerificationCodeCommand extends AuthenticationTokenCommand {

    static constraints = {
    }
}
