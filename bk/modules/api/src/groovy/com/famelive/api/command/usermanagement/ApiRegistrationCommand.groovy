package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.api.enums.ApiUserRegistrationType
import com.famelive.api.enums.ApiUserType
import com.famelive.common.command.usernamagement.RegistrationCommand
import grails.validation.Validateable

@Validateable
class ApiRegistrationCommand extends ApiRequestCommand {
    String email
    String password
    String username
    String fameName
    String mobile
    ApiUserRegistrationType medium
    ApiUserType userType

    static constraints = {
        email nullable: true
        password nullable: true
        username nullable: true
        fameName nullable: true
        mobile nullable: true
        medium nullable: true
    }

    @Override
    RegistrationCommand toRequestCommand() {
        return new RegistrationCommand(email: this.email, password: this?.password, username: this?.username, fameName: this?.fameName, mobile: this?.mobile, medium: this?.medium?.registrationType, userType: this?.userType?.userType)
    }
}
