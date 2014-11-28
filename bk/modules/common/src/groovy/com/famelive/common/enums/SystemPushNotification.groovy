package com.famelive.common.enums

public enum SystemPushNotification {
    PROMOTION('promtionChannel', 'Promotion'),
    NO_SIGNIN('withSignInChannel', 'No Login'),
    SIGNIN('withoutSignInChannel', 'Logined')

    String channelName;
    String displayText

    SystemPushNotification(String channelName, String displayText) {
        this.channelName = channelName;
        this.displayText = displayText;
    }
}