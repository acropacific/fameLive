package com.famelive.api.command

import com.famelive.common.command.RequestCommand
import grails.validation.Validateable

@Validateable
abstract class ApiAuthenticationTokenCommand extends ApiRequestCommand {

    String accessToken
    Long id

    static constraints = {
        accessToken nullable: true
    }

    abstract RequestCommand toRequestCommand()

}
