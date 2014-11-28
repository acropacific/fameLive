package com.famlive

import com.famelive.ChatUtils
import com.famelive.CustomPubnubCallback
import com.pubnub.api.Pubnub
import grails.transaction.Transactional
import org.json.JSONObject

@Transactional
class PubnubChatService {

    ChatUtils chatUtils

    static expose = ['jmx:service=PubnubChat,type=special']

    def pushMsgToPubnub(Map chatInfo) {
        println '*********From PubnubChatService pushMsgToPubnub method*************'
        Pubnub pubnub = chatUtils.getPubnub()
        JSONObject jsonObject = getJSONObjectForPushNotification(chatInfo)
        println('Json::' + jsonObject)
        Set<String> channels = ChatUtils.getChannelsFromMap(chatInfo)
        channels.each { String channel ->
            pubnub.publish(channel, jsonObject, new CustomPubnubCallback())
        }
    }

    private JSONObject getJSONObjectForPushNotification(Map chatInfo) {
        JSONObject jsonObject = ["pn_gcm": getDataFormatMapForAndriod(chatInfo), "aps": getDataFormatMapForIOS(chatInfo)] as JSONObject
        return jsonObject
    }


    Map getDataFormatMapForAndriod(Map chatInfo) {
        Map pushNotificationInfo = [:]
        pushNotificationInfo.put("data", ["message": chatInfo.message])
        return pushNotificationInfo
    }

    Map getDataFormatMapForIOS(Map chatInfo) {
        Map pushNotificationInfo = [:]
        pushNotificationInfo.put("alert", ["action-loc-key": "Open", "body": chatInfo?.message])
        pushNotificationInfo.put("badge", 10)
        pushNotificationInfo.put("sound", "default")
        return pushNotificationInfo
    }

    private JSONObject getJSONObject(Map chatInfo) {
        JSONObject jsonObject = new JSONObject()
        chatInfo.each { key, value ->
            jsonObject.put(key, value)
        }
        return jsonObject
    }
}
