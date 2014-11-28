package com.famelive.common.notification

import com.famelive.common.constant.CommonConstants
import grails.transaction.Transactional

@Transactional
class PushNotificationService {

    def grailsApplication

    public Boolean sendDataToRabbidMQ(Map chatInfo) {
        Boolean pushStatus = true
        try {
            sendMessageQueue(chatInfo)
        } catch (Exception e) {
            println 'Exception' + e
            pushStatus = false
        }
        return pushStatus
    }

    public sendMessageQueue(Map chatInfo) {
        List<String> channels = chatInfo.channels
        channels.each { String channel ->
            chatInfo.put('channels', [channel])
            rabbitSend CommonConstants.RABBIDMQ_QUEUENAME, chatInfo
        }
    }
}
