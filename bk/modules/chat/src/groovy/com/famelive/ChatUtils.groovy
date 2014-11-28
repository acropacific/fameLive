package com.famelive

import com.pubnub.api.Pubnub

class ChatUtils {

    def grailsApplication

    def getPubnub() {
        String publishKey = grailsApplication.famelive.pubnub.publishKey.toString()
        String subscribeKey = grailsApplication.famelive.pubnub.subscribeKey.toString()
        return new Pubnub(publishKey, subscribeKey);
    }

    public static List<String> getChannelsFromMap(Map chatInfo) {
        return chatInfo.channels
    }
}
