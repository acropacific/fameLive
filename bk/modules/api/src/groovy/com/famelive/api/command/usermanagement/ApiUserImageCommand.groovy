package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.common.command.usernamagement.UserImageCommand
import grails.validation.Validateable

@Validateable
class ApiUserImageCommand extends ApiRequestCommand {
    String email
    String imageName

    static constraints = {
        email nullable: true
        imageName nullable: true
    }

    @Override
    UserImageCommand toRequestCommand() {
        return new UserImageCommand(this.properties)
    }
}
