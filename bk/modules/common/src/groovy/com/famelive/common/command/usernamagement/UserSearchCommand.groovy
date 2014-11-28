package com.famelive.common.command.usernamagement

import com.famelive.common.command.PaginationCommand
import com.famelive.common.enums.usermanagement.UserRegistrationType
import grails.validation.Validateable

@Validateable
class UserSearchCommand extends PaginationCommand {
    String fameName
    String email
    String fameId
    List<UserRegistrationType> registrationTypes

    static constraints = {
        fameName nullable: true
        email nullable: true
        fameId nullable: true
        registrationTypes nullable: true
        max nullable: true
        order nullable: true
        sort nullable: true
        offset nullable: true
    }
}
