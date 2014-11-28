package com.famelive.admin.dto.template

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.template.NotificationTemplate
import com.famelive.common.template.SocialTemplate

class AdminNotificationTemplateDetailDto extends AdminResponseDto {

    String notification
    String message

    AdminNotificationTemplateDetailDto() {
    }

    AdminNotificationTemplateDetailDto(NotificationTemplate notificationTemplate) {
        this.message = notificationTemplate?.message
        this.notification = notificationTemplate?.notification?.displayText
    }

    static AdminNotificationTemplateDetailDto createAdminResponseDto(NotificationTemplate notificationTemplate) {
        return new AdminNotificationTemplateDetailDto(notificationTemplate)
    }
}
