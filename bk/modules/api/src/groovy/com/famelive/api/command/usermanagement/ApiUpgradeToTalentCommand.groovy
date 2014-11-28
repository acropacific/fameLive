package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.usernamagement.UpgradeToTalentCommand
import grails.validation.Validateable

@Validateable
class ApiUpgradeToTalentCommand extends ApiAuthenticationTokenCommand {
    String fameName

    static constraints = {
        fameName nullable: true
    }

    @Override
    UpgradeToTalentCommand toRequestCommand() {
        return new UpgradeToTalentCommand(id: this?.id, fameName: this?.fameName)
    }
}
