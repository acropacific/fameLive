package com.famelive.web.command

import com.famelive.common.command.usernamagement.RegistrationCommand
import grails.validation.Validateable

@Validateable
class WebRegistrationCommand extends WebRequestCommand {

    String email
    String password
    String username
    String fameName
    String mobile
    String imageName

    static constraints = {
        email nullable: true
        password nullable: true
        username nullable: true
        fameName nullable: true
        mobile nullable: true
        imageName nullable: true
    }

    @Override
    RegistrationCommand toRequestCommand() {
        return new RegistrationCommand(this.properties)
    }
}
