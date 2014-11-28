package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import grails.validation.Validateable

@Validateable
class CheckUserAccountCommand extends AuthenticationTokenCommand {

    static constraints = {
    }
}
