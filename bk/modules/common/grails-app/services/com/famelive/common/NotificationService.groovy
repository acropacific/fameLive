package com.famelive.common

import com.famelive.common.command.notification.FetchNotificationChannelsCommand
import com.famelive.common.dto.notification.FetchNotificationChannelsDto
import com.famelive.common.enums.SystemPushNotification
import com.famelive.common.exceptions.CommonException
import com.famelive.common.user.User
import grails.transaction.Transactional

@Transactional
class NotificationService {

    FetchNotificationChannelsDto fetchNotificationChannels(FetchNotificationChannelsCommand fetchNotificationChannelsCommand) throws CommonException {
        User user = User.get(fetchNotificationChannelsCommand?.id)
        FetchNotificationChannelsDto fetchNotificationChannelsDto = new FetchNotificationChannelsDto(channels: fetchNotificationChannelsForUser(user, fetchNotificationChannelsCommand.channelFlag))
        return fetchNotificationChannelsDto
    }

    List<String> fetchNotificationChannelsForUser(User user, Boolean getSystemChannels) {
        List<String> channels = []
        if (user) channels += getUserSpecificChannels(user)
        if (getSystemChannels) channels += getSystemSpecificChannelsForUser(user)
        return channels
    }

    List<String> getUserSpecificChannels(User user) {
        return [user.channel]
    }

    List<String> getSystemSpecificChannelsForUser(User user) {
        //TODO need to implements rules
        println 'getSystemSpecificChannelsForUser::' + SystemPushNotification.PROMOTION.channelName
        return [SystemPushNotification.PROMOTION.channelName] as List<String>
    }

}
