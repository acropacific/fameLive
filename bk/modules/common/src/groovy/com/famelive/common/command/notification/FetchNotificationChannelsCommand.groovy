package com.famelive.common.command.notification

import com.famelive.common.command.AuthenticationTokenCommand
import grails.validation.Validateable

@Validateable
class FetchNotificationChannelsCommand extends AuthenticationTokenCommand {

    Boolean channelFlag

    static constraints = {
    }
}
