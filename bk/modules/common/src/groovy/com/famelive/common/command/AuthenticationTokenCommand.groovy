package com.famelive.common.command

import grails.validation.Validateable

@Validateable
class AuthenticationTokenCommand extends RequestCommand {

    String accessToken
    Long id

    static constraints = {
        accessToken nullable: true
    }
}
