package com.famelive.admin.enums

import com.famelive.common.enums.SystemPushNotification

public enum AdminPushNotification {

    PROMOTION('Promotion', [SystemPushNotification.PROMOTION]),
    CREATE_EVENT('Create Event', [SystemPushNotification.CREATE_EVENT]),
    EDIT_EVENT('Edit Event', [SystemPushNotification.EDIT_EVENT]),
    CANCEL_EVENT('Cancel Event', [SystemPushNotification.CANCEL_EVENT]),
    ALL('Cancel Event', SystemPushNotification.values() as List)

    String displayText
    List<SystemPushNotification> notificationList

    AdminPushNotification(String displayText, List<SystemPushNotification> notificationList) {
        this.displayText = displayText;
        this.notificationList = notificationList
    }

}
