package com.famelive.api.command.followermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.followermanagement.FollowPerformerCommand
import com.famelive.common.command.followermanagement.UnFollowPerformerCommand
import grails.validation.Validateable

@Validateable
class ApiUnFollowPerformerCommand extends ApiAuthenticationTokenCommand {
    Long userId

    static constraints = {
        userId nullable: true
    }

    @Override
    UnFollowPerformerCommand toRequestCommand() {
        return new UnFollowPerformerCommand(id: this?.id, performerId: this?.userId)
    }
}
