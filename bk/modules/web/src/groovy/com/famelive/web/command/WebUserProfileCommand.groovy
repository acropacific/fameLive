package com.famelive.web.command

import com.famelive.common.command.usernamagement.UserProfileCommand
import grails.validation.Validateable

@Validateable
class WebUserProfileCommand extends WebRequestCommand {

    Long id
    String email
    String username
    String fameName
    String mobile
    String imageName

    static constraints = {
        username nullable: true
        fameName nullable: true
        mobile nullable: true
        imageName nullable: true
    }

    @Override
    UserProfileCommand toRequestCommand() {
        return new UserProfileCommand(id: this?.id, email: this?.email, username: this?.username, fameName: this?.fameName, mobile: this?.mobile, imageName: this.imageName)
    }
}
