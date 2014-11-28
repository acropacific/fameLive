package com.famelive.admin.dto.template

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.dto.socialtemplate.NotificationTemplateDto
import com.famelive.common.template.NotificationTemplate

class AdminNotificationTemplateDto extends AdminResponseDto {

    List<AdminNotificationTemplateDetailDto> adminNotificationTemplateDetailDtoList

    AdminNotificationTemplateDto() {}

    AdminNotificationTemplateDto(NotificationTemplateDto notificationTemplateDto) {
        this.adminNotificationTemplateDetailDtoList = populateNotificationTemplateDetailDto(notificationTemplateDto)
    }

    static AdminNotificationTemplateDto createCommonResponseDto(NotificationTemplateDto notificationTemplateDto) {
        return new AdminNotificationTemplateDto(notificationTemplateDto)
    }

    static List<AdminNotificationTemplateDetailDto> populateNotificationTemplateDetailDto(NotificationTemplateDto notificationTemplateDto) {
        List<AdminNotificationTemplateDetailDto> adminSocialTemplateDetailDtoList = []
        notificationTemplateDto?.notificationTemplates?.each { NotificationTemplate notificationTemplate ->
            adminSocialTemplateDetailDtoList << AdminNotificationTemplateDetailDto.createAdminResponseDto(notificationTemplate)
        }
        return adminSocialTemplateDetailDtoList
    }
}
