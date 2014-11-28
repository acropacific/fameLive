package com.famelive.api.command.chat

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.chat.FetchChatChannelCommand
import grails.validation.Validateable

@Validateable
class ApiFetchChatChannelCommand extends ApiAuthenticationTokenCommand {

    String eventId

    static constraints = {
        eventId nullable: true
    }

    @Override
    FetchChatChannelCommand toRequestCommand() {
        return new FetchChatChannelCommand(id: this?.id, eventId: this.eventId)
    }
}
