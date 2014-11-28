package com.famlive

import com.famelive.constant.ChatConstants
import grails.transaction.Transactional

@Transactional
class RabbitMQMessageReceiverService {

    PubnubChatService pubnubChatService

    static rabbitQueue = ChatConstants.RABBIDMQ_QUEUENAME

    void handleMessage(Map chatInfo) {
        println 'From RabbitMQMessageReceiverService handleMessage' + chatInfo
        pubnubChatService.pushMsgToPubnub(chatInfo)
    }
}
