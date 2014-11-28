package com.famelive.api.command.notification

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.common.command.notification.FetchNotificationChannelsCommand
import grails.validation.Validateable

@Validateable
class ApiFetchNotificationChannelsCommand extends ApiAuthenticationTokenCommand {

    Boolean channelFlag

    @Override
    FetchNotificationChannelsCommand toRequestCommand() {
        return new FetchNotificationChannelsCommand(id: this?.id, channelFlag: this.channelFlag)
    }
}
