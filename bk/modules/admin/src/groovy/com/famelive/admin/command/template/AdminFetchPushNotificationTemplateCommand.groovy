package com.famelive.admin.command.template

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.admin.enums.AdminPushNotification
import com.famelive.common.command.template.FetchPushNotificationTemplateCommand

class AdminFetchPushNotificationTemplateCommand extends AdminAuthenticationCommand {

    AdminPushNotification adminPushNotification

    @Override
    FetchPushNotificationTemplateCommand toRequestCommand() {
        return new FetchPushNotificationTemplateCommand(id: this?.id, notifications: adminPushNotification?.notificationList)
    }
}
