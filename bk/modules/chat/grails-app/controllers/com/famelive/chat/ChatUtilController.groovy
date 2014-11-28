package com.famelive.chat

import com.famelive.command.DataPushCommand
import com.famlive.PubnubChatService
import grails.converters.XML
import org.json.JSONObject

class ChatUtilController {

    ChatUtilService chatUtilService
    PubnubChatService pubnubChatService

    def index() {
    }

    def chat() {
        String name = params.name ? params.name : 'UnKnown'
        String channel = params.chanel ? params.chanel : '1'
        [userName: name, channel: channel]
    }

    def chatForAdmin() {
        String name = params.name ? params.name : 'Admin'
        String channel = params.chanel ? params.chanel : '10'
        [userName: name, channel: channel]
    }

    def sendDataToPubnub(DataPushCommand dataPushCommand) {
        Map chatInfo = chatUtilService.getChatInfo(dataPushCommand)
        Boolean pushNotificationStatus = chatUtilService.pushToPubnubByJMX(chatInfo)
        render pushNotificationStatus as XML
    }

    def sendToPubnub(DataPushCommand dataPushCommand) {
        Map chatInfo = [:]
        chatInfo.put("message", dataPushCommand.message)
        chatInfo.put("senderName", dataPushCommand.senderName)
        chatInfo.put("channels", dataPushCommand.channels)
        pubnubChatService.pushMsgToPubnub(chatInfo)
        render true as XML
    }

    def broadcastMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", params.message)
        jsonObject.put("senderName", params.senderName)

        (1..10).each {
            jsonObject.put("channel", it.toString())
            pubnubChatService.pushMsgToPubnub(jsonObject)
        }
        render 'success'
    }
}
