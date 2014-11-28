package com.famelive

import com.famelive.api.constant.ApiConstants
import com.famelive.api.util.ApiMessagesUtil
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class TestApiController {

    def grailsApplication

    def index() {
        render ApiMessagesUtil.messageSource.getProperty("common_error")
    }

    def sendMessage() {
        println '*********From TestApiController sendMessage Method*************'
        Map chatInfo = [:]
        chatInfo.senderName = params.senderName ? params.senderName : 'Admin'
        chatInfo.message = params.message ? params.message : 'Hello Admin'
        chatInfo.channels = params.channels ? params.channels : ['1', '2']
        println chatInfo
        rabbitSend ApiConstants.RABBIDMQ_QUEUENAME, chatInfo
    }
}
