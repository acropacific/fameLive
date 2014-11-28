package com.famelive.common.dto.socialtemplate

import com.famelive.common.dto.ResponseDto
import com.famelive.common.template.NotificationTemplate
import com.famelive.common.template.SocialTemplate

class NotificationTemplateDto extends ResponseDto {

    List<NotificationTemplate> notificationTemplates

    NotificationTemplateDto() {}

    NotificationTemplateDto(List<NotificationTemplate> notificationTemplates) {
        this.notificationTemplates = notificationTemplates
    }

    static NotificationTemplateDto createCommonResponseDto(List<NotificationTemplate> notificationTemplates) {
        return new NotificationTemplateDto(notificationTemplates)
    }

}
