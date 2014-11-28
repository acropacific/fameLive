package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.UserProfileCommand
import grails.validation.Validateable

@Validateable
class ApiUserProfileCommand extends ApiAuthenticationTokenCommand {
    String email
    String username
    String fameName
    String mobile
    String imageName

    static constraints = {
        email nullable: true
        username nullable: true
        mobile nullable: true
        imageName nullable: true
        fameName nullable: true
    }

    @Override
    UserProfileCommand toRequestCommand() {
        return new UserProfileCommand(id: this.id, email: this.email, username: this.username, fameName: this.fameName, mobile: this.mobile, imageName: this.imageName)
    }
}
