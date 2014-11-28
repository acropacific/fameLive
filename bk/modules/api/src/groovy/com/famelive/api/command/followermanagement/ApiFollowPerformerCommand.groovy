package com.famelive.api.command.followermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.followermanagement.FollowPerformerCommand
import grails.validation.Validateable

@Validateable
class ApiFollowPerformerCommand extends ApiAuthenticationTokenCommand {
    Long userId

    static constraints = {
        userId nullable: true
    }

    @Override
    FollowPerformerCommand toRequestCommand() {
        return new FollowPerformerCommand(id: this?.id, performerId: this?.userId)
    }
}
