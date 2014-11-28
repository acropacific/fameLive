package com.famelive.common.enums

public enum SystemPushNotification {
    PROMOTION('promotionChannel', 'Promotion', true, 'Promotional Push'),
    CREATE_EVENT('createEvent', 'Create Event', false, 'New Event Created:  [EVENT_NAME] '),
    EDIT_EVENT('editEvent', 'Edit Event', false, '[EVENT_NAME] Event Updated'),
    CANCEL_EVENT('cancelEvent', 'Cancel Event', false, '[EVENT_NAME] Event Cancelled')

    String channelName;
    String displayText
    Boolean isPublic
    String message

    SystemPushNotification(String channelName, String displayText, Boolean isPublic, String message) {
        this.channelName = channelName;
        this.displayText = displayText;
        this.isPublic = isPublic
        this.message = message
    }

    static List<SystemPushNotification> getPublicSystemPushNotification() {
        List<SystemPushNotification> systemPushNotifications = SystemPushNotification.values().findAll {
            it.isPublic == true
        }
        return systemPushNotifications
    }
}