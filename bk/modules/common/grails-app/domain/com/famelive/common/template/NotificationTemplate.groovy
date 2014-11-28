package com.famelive.common.template

import com.famelive.common.enums.SystemPushNotification

class NotificationTemplate {
    SystemPushNotification notification
    String message

    static constraints = {
        notification nullable: false
        message nullable: false
    }

    static mapping = {
        message type: 'text'
    }
}
