package com.famelive.common.command.chat

import com.famelive.common.command.AuthenticationTokenCommand
import grails.validation.Validateable

@Validateable
class FetchChatChannelCommand extends AuthenticationTokenCommand {

    String eventId

    static constraints = {
        eventId nullable: true
    }
}