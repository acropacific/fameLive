package com.famelive.api.command

import com.famelive.common.command.RequestCommand
import grails.validation.Validateable

@Validateable
class ApiHandleRequestCommand extends ApiRequestCommand {

    RequestCommand toRequestCommand() {
        return null
    }
}
