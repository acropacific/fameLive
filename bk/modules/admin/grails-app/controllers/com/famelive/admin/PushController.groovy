package com.famelive.admin

import com.famelive.admin.command.push.ChatInfoCommand
import com.famelive.common.enums.SystemPushNotification
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PushController {

    def pushNotificationService

    def index() {
        [notificationTypes: SystemPushNotification.values(), user: 'Admin', chatInfoCommand: params.chatInfoCommand, pushStatusMessage: params.pushStatusMessage]
    }

    def sendPushNotification(ChatInfoCommand chatInfoCommand) {
        Boolean pushStatus = false
        if (chatInfoCommand.validate()) {
            pushStatus = pushNotificationService.sendDataToJMX(getChatInfo(chatInfoCommand))
        }
        String pushStatusMessage = getPushStatusMessage(pushStatus, chatInfoCommand)
        redirect action: 'index', params: [pushStatusMessage: pushStatusMessage]
    }

    public String getPushStatusMessage(Boolean pushStatus, ChatInfoCommand chatInfoCommand) {
        String pushStatusMessage = ''
        if (!chatInfoCommand.validate())
            pushStatusMessage = 'Fields can not be empty'
        else {
            if (pushStatus)
                pushStatusMessage = "Message sent successfully:${chatInfoCommand.message}"
            else
                pushStatusMessage = "Error while sending push notification"
        }
        return pushStatusMessage
    }

    public Map getChatInfo(ChatInfoCommand chatInfoCommand) {
        log.info('*********From getChatInfo Method*************')
        Map chatInfo = [:]
        chatInfo.senderName = chatInfoCommand.senderName ? chatInfoCommand.senderName : 'Admin'
        chatInfo.message = chatInfoCommand.message
        chatInfo.channels = chatInfoCommand.channels
        return chatInfo
    }
}
