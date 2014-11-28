package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.common.command.usernamagement.FameNameCommand
import grails.validation.Validateable

@Validateable
class ApiCheckFameNameAvailabilityCommand extends ApiRequestCommand {

    String fameName

    static constraints = {
        fameName nullable: true
    }

    @Override
    FameNameCommand toRequestCommand() {
        return new FameNameCommand(fameName: this?.fameName)
    }
}
